package final2.tag.value;

import final2.tag.TagType;

/**
 * a value of a multivalue-tag, meaning with a string as a value
 * 
 * @author robin
 * @version 1
 */
public class MultivalueTagValue extends TagValue {

    private final String value;

    /**
     * create a new value from the string
     * 
     * @param value
     *            the value string
     */
    MultivalueTagValue(String value) {
        super(TagType.MultivalueTag);
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultivalueTagValue) {
            MultivalueTagValue other = (MultivalueTagValue) obj;
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
    public int hashCode() {
        // needs to be overridden to separate "undefined" and undefined
        final int prime = 31;
        int result = 1;
        result = prime * result + getType().hashCode();
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public String toString() {
        if (value == null) {
            return UNDEFINED;
        } else {
            return value;
        }
    }
}