package final2.tag.value;

import final2.Util;
import final2.tag.TagType;

/**
 * a value of a tag
 * 
 * @author robin
 * @version 1
 */
public abstract class TagValue {
    /**
     * the value that is printed when the tag is undefined
     */
    protected static final String UNDEFINED = "undefined";
    private final TagType type;

    /**
     * create a new tag value of the specified type
     * 
     * @param type
     *            the type
     */
    protected TagValue(TagType type) {
        this.type = type;
    }

    /**
     * create a new value from the string, detecting the value type or using the
     * given type
     * 
     * @param value
     *            the value as a string, null means undefined
     * @param type
     *            the type of this value, TagType.Unknown if unknown
     * @return the new tagValue
     */
    public static TagValue create(String value, TagType type) {
        TagType tagType = type;
        if (value != null) { // check type for non-undefined tag values
            TagType inferredType = TagType.inferFromValueString(value);
            if (tagType != inferredType) {
                Util.handleError(value + " does not have type " + type);
            }
        }
        TagValue v;
        switch (tagType) {
        case BooleanTag:
            v = new BooleanTagValue(value != null);
            break;
        case IntegerTag:
            v = new IntegerTagValue(value == null ? null
                    : Integer.parseInt(value));
            break;

        case MultivalueTag:
            v = new MultivalueTagValue(value);
            break;
        case Invalid:
        default:
            throw new RuntimeException("invalid tag type " + tagType
                    + " for value " + value);
        }
        return v;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getType().hashCode();
        result = prime * result + toString().hashCode();
        return result;
    }

    @Override
    public abstract boolean equals(Object obj);

    /**
     * compares this value to a value string and its type
     * 
     * @param otherValue
     *            a string
     * @param type
     *            the type of the other string, must not be unknown
     * @return if this tag is equal to the other
     */
    public boolean equals(String otherValue, TagType type) {
        String value = toString();
        if (otherValue == null) {
            return value == null && this.type == type;
        }
        if (value != null && value.equals(otherValue)) {
            return this.type == type;
        } else {
            return false;
        }
    }

    @Override
    public abstract String toString();

    /**
     * @return the tag type of this value
     */
    public TagType getType() {
        return type;
    }
}