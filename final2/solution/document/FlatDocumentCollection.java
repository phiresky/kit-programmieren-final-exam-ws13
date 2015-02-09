package final2.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import final2.Util;
import final2.tag.Tag;
import final2.tag.value.TagValue;

/**
 * a document collection without a tree structure. All documents are in the top
 * level of this collection
 * 
 * @author robin
 * @version 1
 */
public class FlatDocumentCollection extends DocumentCollection {

    /**
     * the minimum gain that needs to be achieved by {@link #splitBy(Tag)} to
     * justify splitting
     */
    private static final double MINGAIN = 10e-3;
    private final List<Document> list;
    /** total open count of this list */
    private int totalOpenCount;

    /**
     * create a new empty flat document collection
     */
    public FlatDocumentCollection() {
        list = new ArrayList<Document>();
    }

    /**
     * create a new document collection from all documents of another collection
     * which have a specific tag value
     * 
     * @param tag
     *            the tag
     * @param source
     *            the source collection
     * @param value
     *            the value which documents in the collection must have
     */
    public FlatDocumentCollection(DocumentCollection source, Tag tag,
            TagValue value) {
        list = new ArrayList<Document>();
        for (Document d : source) {
            if (value.equals(d.getTag(tag))) {
                add(d);
            }
        }
    }

    /**
     * split this collection by the values of a specific tag of the documents
     * 
     * @param tag
     *            the tag by which to split
     * @return a new collection of all documents in this collection split by tag
     */
    public SplitDocumentCollection splitBy(Tag tag) {
        return new SplitDocumentCollection(this, tag);
    }

    /**
     * recursively split this collection until {@link #MINGAIN} is reached
     * 
     * @param tags
     *            the available tags, possibly a {@link final2.tag.TagManager}
     * @param outputPrefix
     *            the output with which the documents should be output while
     *            splitting
     * @return a new, split document collection or the same one if splitting is
     *         unnecessary
     */
    public DocumentCollection recursiveSplit(Iterable<Tag> tags,
            String outputPrefix) {
        Tag bestTag = null;
        double maxGain = 0;
        for (Tag tag : tags) {
            double currentGain = exceptedInformationGain(tag);
            if (currentGain > maxGain) {
                maxGain = currentGain;
                bestTag = tag;
            }
            if (currentGain > MINGAIN) {
                System.out.println(String.format("%s%s=%.2f", outputPrefix,
                        tag.getName(), currentGain));

            }

        }
        if (maxGain > MINGAIN) {
            String tagName = bestTag.getName();
            SplitDocumentCollection splits = this.splitBy(bestTag);
            for (Map.Entry<TagValue, DocumentCollection> splitEntry : splits
                    .byValue()) {
                TagValue tagValue = splitEntry.getKey();
                FlatDocumentCollection flatCollection = (FlatDocumentCollection) splitEntry
                        .getValue();
                // flatCollection is still a flat document collection because we
                // only split on the first level using splitBy()
                DocumentCollection splitCollection = flatCollection
                        .recursiveSplit(tags, outputPrefix + tagName + "="
                                + tagValue + "/");
                // now splitCollection is the fully split collection, so we
                // replace
                // the flat one with the new one in the list
                splitEntry.setValue(splitCollection);
            }
            return splits;
        } else {
            return this;
        }
    }

    @Override
    public int getOpenCount() {
        return totalOpenCount;
    }

    /**
     * add a document to this collection
     * 
     * @param d
     *            the document
     */
    public void add(Document d) {
        for (Document doc : list) {
            if (doc.getName().equals(d.getName())) {
                Util.handleError("Duplicate document identifier: "
                        + doc.getName());
            }
        }
        totalOpenCount += d.getOpenCount();
        list.add(d);
    }

    @Override
    public Iterator<Document> iterator() {
        return list.iterator();
    }

    @Override
    public double remainingUncertainty(Tag tag) {
        double result = 0;
        for (Map.Entry<TagValue, DocumentCollection> v : splitBy(tag).byValue()) {
            DocumentCollection dt = v.getValue();
            result += dt.getOpenCount() / (double) getOpenCount()
                    * dt.uncertainty();
        }
        return result;
    }

    @Override
    public String toString(String prefix) {
        StringBuilder sb = new StringBuilder();
        for (Document d : this) {
            sb.append(prefix);
            sb.append('"' + d.getName() + '"');
            sb.append('\n');
        }
        return sb.toString();
    }

}
