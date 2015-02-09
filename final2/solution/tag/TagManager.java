package final2.tag;

import java.util.Iterator;
import java.util.Set;

import final2.Util;

/**
 * A manager for tags. Manages a collection of tags, avoiding duplicates and
 * providing methods for iterating over tags and their possible values
 * 
 * @author robin
 * @version 1
 */
public class TagManager implements Iterable<Tag> {
    /**
     * a set of weak references to the tags, when they are not used anymore
     * elsewhere (in TagMaps), they are automatically garbage collected by java
     */
    private final Set<Tag> tags;

    /**
     * initialize a new, empty tag manager
     */
    public TagManager() {
        tags = Util.<Tag>createWeakSet();
    }

    /**
     * get a tag by its name
     * 
     * @param name
     *            the string uniquely identifying this tag
     * @return the tag, null if it was not found
     */
    public Tag get(String name) {
        String lowerName = name.toLowerCase();
        for (Tag tag : tags) {
            if (tag.getName().toLowerCase().equals(lowerName)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * create a tag with a type
     * 
     * @param name
     *            the name of the tag
     * @param type
     *            the type of the tag
     * @return the tag
     */
    public Tag create(String name, TagType type) {
        Tag tag = new Tag(name, type);
        boolean success = tags.add(tag);
        if (!success) {
            Util.handleError("tag " + name + " already exists");
        }
        return tag;
    }

    /**
     * get a tag by its name, creating it if it was not used before, throwing an
     * error if the tag exists but has an invalid tag type
     * 
     * @param name
     *            the string uniquely identifying this tag
     * @param type
     *            the type of the tag
     * @return the tag
     */
    public Tag getOrCreate(String name, TagType type) {
        Tag tag = get(name);
        if (tag != null && tag.getType() != type) {
            Util.handleError("invalid tag type " + type + " for tag " + name);
        } else if (tag == null) {
            tag = create(name, type);
        }
        return tag;
    }

    @Override
    public Iterator<Tag> iterator() {
        return tags.iterator();
    }
}