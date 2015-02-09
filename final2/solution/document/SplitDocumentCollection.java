package final2.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import final2.Util;
import final2.tag.Tag;
import final2.tag.value.TagValue;

/**
 * A collection of documents, split by their values for a specific tag into
 * subcollections
 * 
 * @author robin
 * @version 1
 */
public class SplitDocumentCollection extends DocumentCollection {

    /**
     * the tag by which the documents are categorized in this collection
     */
    private final Tag splitBy;
    private final Map<TagValue, DocumentCollection> splits;

    /**
     * Create a new {@link SplitDocumentCollection} from any other
     * {@link DocumentCollection}, splitting it by the tag
     * 
     * @param documents
     *            the collection of documents to use. This collection will not
     *            be changed
     * @param tag
     *            the tag by which this collection will be categorized
     */
    protected SplitDocumentCollection(DocumentCollection documents, Tag tag) {
        splits = new HashMap<>();
        splitBy = tag;
        for (TagValue value : splitBy) {
            DocumentCollection dt = new FlatDocumentCollection(documents, tag,
                    value);
            splits.put(value, dt);
        }
    }

    /**
     * Iterate over the documents by the value of the tag by which they are
     * split in this collection
     * 
     * @return the set containing the tag value with each associated collection
     *         of documents
     */
    public Iterable<Entry<TagValue, DocumentCollection>> byValue() {
        return splits.entrySet();
    }

    @Override
    public Iterator<Document> iterator() {
        Collection<Document> all = new ArrayList<Document>();
        for (DocumentCollection coll : splits.values()) {
            for (Document d : coll) {
                all.add(d);
            }
        }
        return all.iterator();
    }

    @Override
    public int getOpenCount() {
        int opencount = 0;
        for (DocumentCollection coll : splits.values()) {
            opencount += coll.getOpenCount();
        }
        return opencount;
    }

    @Override
    public double remainingUncertainty(Tag tag) {
        Util.handleError("this collection is already split");
        return 0;
    }

    @Override
    public String toString(String prefix) {
        StringBuilder sb = new StringBuilder();
        for (Entry<TagValue, DocumentCollection> entry : byValue()) {
            sb.append(entry.getValue().toString(
                    String.format("%s%s=%s/", prefix, splitBy.getName(),
                            entry.getKey())));
        }
        return sb.toString();
    }
}
