package final2.tag.value;

import final2.tag.TagType;

/**
 * a boolean tag value, either set or not set, printed as "defined" or
 * "undefined"
 *
 * @author robin
 * @version 1
 *
 */
public final class BooleanTagValue extends TagValue {
    /**
     * the value a binary tag has when it is set, must not also be a valid
     * MultivalueTag, when it is unset it is null
     */
    public static final String DEFINED = "";
    /** nullable int */
    private final boolean value;

    /**
     * create a new boolean tag value
     *
     * @param value
     *            the value
     */
    public BooleanTagValue(boolean value) {
        super(TagType.BooleanTag);
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BooleanTagValue) {
            BooleanTagValue other = (BooleanTagValue) obj;
            return value == other.value;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(String other, TagType otherType) {
        if (otherType != TagType.BooleanTag) {
            return false;
        }
        boolean otherTrue = other != null;
        return otherTrue == value;
    }

    @Override
    public String toString() {
        return value ? "defined" : UNDEFINED;
    }

}
