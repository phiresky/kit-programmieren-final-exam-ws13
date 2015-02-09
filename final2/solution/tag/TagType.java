package final2.tag;

import final2.FormatRules;

public enum TagType {
    /** a tag that is either set or not set */
    BooleanTag,
    /** a tag containing a whole number */
    IntegerTag,
    /**
     * a tag containing a string of any characters, only the first char may not
     * be a digit
     */
    MultivalueTag,
    /** used to indicate an unrecognized/invalid tag type */
    Invalid;

    /**
     * detect weather this value is a numeric,string or boolean tag
     * 
     * @param string
     *            the input string, must not be null
     * @return the tag type of the string
     */
    public static TagType inferFromValueString(String string) {
        TagType type = Invalid;

        if (string.matches(FormatRules.BOOLEAN_TAG_VALUE)) {
            type = BooleanTag;
        } else if (string.matches(FormatRules.INTEGER_TAG_VALUE)) {
            try {
                Integer.parseInt(string);
                type = IntegerTag;
            } catch (NumberFormatException e) {
                // Invalid
            }
        } else if (string.matches(FormatRules.MULTIVALUE_TAG_VALUE)) {
            type = MultivalueTag;
        }

        return type;

    }
}
