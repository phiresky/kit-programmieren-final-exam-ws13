package final2.tag.value;

import final2.tag.TagType;

/**
 * represents an integer value of a tag
 * 
 * @author robin
 * @version 1
 */
public class IntegerTagValue extends TagValue {
    /** nullable integer */
    private final Integer value;

    /**
     * create a new integer tag value
     * 
     * @param value
     *            the value
     */
    public IntegerTagValue(Integer value) {
        super(TagType.IntegerTag);
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntegerTagValue) {
            IntegerTagValue other = (IntegerTagValue) obj;
            if (value == null) {
                return other.value == null;
            } else if (value.equals(other.value)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (value == null) {
            return UNDEFINED;
        } else {
            return Integer.toString(value);
        }
    }

}
