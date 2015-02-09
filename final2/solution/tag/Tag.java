package final2.tag;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import final2.Util;
import final2.tag.value.TagValue;

/**
 * representation of a tag, identified by a string. Can be iterated to get all
 * used values of the tag.
 * 
 * @author robin
 * @version 1
 * 
 */
public final class Tag implements Iterable<TagValue> {
    /**
     * the identifying string for this tag. The casing is retained, but ignored
     * when comparing to other tags
     */
    private final String name;
    /**
     * a set of weak references to the values of this tag, when they are not
     * used anymore elsewhere (in Documents/TagMaps), they are automatically
     * garbage collected by java
     */
    private final Set<TagValue> values;
    private final TagType type;
    /** keep a strong reference of the undefined value in memory */
    private final TagValue undefined;

    /**
     * create a new tag
     * 
     * @param name
     *            the tags name
     * @param type
     *            the tags type
     */
    Tag(String name, TagType type) {
        this.name = name;
        this.type = type;
        values = Util.<TagValue>createWeakSet();
        undefined = TagValue.create(null, type);
        values.add(undefined); // add the undefined value
    }

    /**
     * @return the unique name of this tag
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag) {
            return name.toLowerCase().equals(((Tag) obj).name.toLowerCase());
        } else {
            return false;
        }

    }

    /**
     * find a value for this tag, creating it if it was not previously used
     * 
     * @param valueString
     *            the name of the value
     * @return the value
     */
    public TagValue get(String valueString) {
        for (TagValue value : values) {
            if (value.equals(valueString, type)) {
                return value;
            }
        }
        TagValue value = TagValue.create(valueString, type);
        values.add(value);
        return value;
    }

    @Override
    public Iterator<TagValue> iterator() {
        return values.iterator();
    }

    /**
     * get all values this tag has
     * 
     * @return the value
     */
    public Collection<TagValue> getValues() {
        return values;
    }

    /**
     * @return the type of this tag
     */
    public TagType getType() {
        return type;
    }

}