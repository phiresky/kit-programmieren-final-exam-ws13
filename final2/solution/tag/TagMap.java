package final2.tag;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import final2.Util;
import final2.tag.value.TagValue;

/**
 * A Map of tags to values
 * 
 * @author robin
 * @version 1
 */
public class TagMap implements Iterable<Map.Entry<Tag, TagValue>> {

    private final Map<Tag, TagValue> map;
    private final TagManager manager;

    /**
     * create a new TagMap
     * 
     * @param manager
     *            the manager containing all possible tags
     */
    public TagMap(TagManager manager) {
        this.manager = manager;
        map = new HashMap<Tag, TagValue>();
    }

    /**
     * set the value of a tag
     * 
     * @param tag
     *            the tag
     * @param value
     *            the value
     * @return true if the tag was set before
     */
    public boolean set(Tag tag, TagValue value) {
        if (tag.getType() != value.getType()) {
            Util.handleError(String.format(
                    "Tag %s has type %s, tried to add value of type %s",
                    tag.getName(), tag.getType(), value.getType()));
        }
        TagValue before = map.put(tag, value);
        return before != null;
    }

    /**
     * remove a tag and it's value from the list
     * 
     * @param tag
     *            the tag
     * @return the value the tag had, null if it was not in the list
     */
    public TagValue remove(Tag tag) {
        return map.remove(tag);
    }

    @Override
    public Iterator<Entry<Tag, TagValue>> iterator() {
        return map.entrySet().iterator();
    }

    /**
     * @param tag
     *            get the value of this tag
     * @return the value
     */
    public TagValue get(Tag tag) {
        TagValue value = map.get(tag);
        if (value == null) {
            value = tag.get(null);
        }
        return value;
    }

    /**
     * @return the tag manager used by this tag map
     */
    public TagManager getManager() {
        return manager;
    }

    /**
     * convenience method for setting a tag value by strings. if the tag was
     * already set, an error will be shown
     * 
     * @param tagName
     *            the tag
     * @param valueString
     *            the value
     * @param type
     *            the type of the tag
     * @return true if the tag was set before
     */
    public boolean set(String tagName, String valueString, TagType type) {
        Tag tag = manager.getOrCreate(tagName, type);
        TagValue value = tag.get(valueString);
        boolean setBefore = set(tag, value);
        if (setBefore) {
            Util.handleError(String.format(
                    "Tag %s was set before trying to set it to %s", tagName,
                    valueString));
        }
        return setBefore;
    }

    /**
     * convenience method for removing a tag
     * 
     * @param tag
     *            remove a value by its tags name
     * @return the value the tag had, null if it was undefined
     */
    public TagValue remove(String tag) {
        return remove(manager.get(tag));
    }
}