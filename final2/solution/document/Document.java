package final2.document;

import java.util.Map;

import final2.tag.IntegerTagRule;
import final2.tag.Tag;
import final2.tag.TagMap;
import final2.tag.TagType;
import final2.tag.value.BooleanTagValue;
import final2.tag.value.TagValue;

/**
 * A document in a file system
 * 
 * @author robin
 * @version 1
 */
public class Document {
    /** unique document identifier / file name */
    private final String name;
    private final DocumentType fileType;

    private final TagMap tags;
    private final int openCount;

    /**
     * initialize a new document. The given map of tags will be adjusted
     * according to the ass2 rules based on the file type
     * 
     * @param name
     *            the file name to set
     * @param filetype
     *            the file type
     * @param openCount
     *            the amount of times this file was opened
     * @param tags
     *            a map of tags
     */
    public Document(String name, String filetype, int openCount, TagMap tags) {
        this.name = name;
        this.fileType = DocumentType.parse(filetype);
        this.openCount = openCount;
        this.tags = tags;
        if (this.fileType != DocumentType.IMAGE
                && this.fileType != DocumentType.PROGRAM) {
            Tag genreTag = tags.getManager().get("genre");
            if (genreTag != null && genreTag.getType() == TagType.MultivalueTag) {
                TagValue genre = tags.remove(genreTag);
                if (genre != null) {
                    tags.set(filetype + "genre", genre.toString(),
                            TagType.MultivalueTag);
                }
            }
        }

        if (filetype.equals("program")) {
            tags.set("executable", BooleanTagValue.DEFINED, TagType.BooleanTag);
        }
        IntegerTagRule.applyAll(this);
    }

    @Override
    public String toString() {
        String s = name + "," + fileType + "," + openCount;
        for (Map.Entry<Tag, TagValue> entry : tags) {
            s += "," + entry.getKey().getName() + "=" + entry.getValue();
        }
        return s;
    }

    /**
     * @return the filename of this document
     */
    public String getName() {
        return name;
    }

    /**
     * @return the amount of times this document was opened
     */
    public int getOpenCount() {
        return openCount;
    }

    /**
     * get the value of a tag of this document
     * 
     * @param tag
     *            the tag
     * @return the value
     */
    public TagValue getTag(Tag tag) {
        return tags.get(tag);
    }

    /**
     * @return the filetype of this document
     */
    public DocumentType getFiletype() {
        return fileType;
    }

    /**
     * @return the {@link TagMap} of this document
     */
    public TagMap getTags() {
        return tags;
    }
}
