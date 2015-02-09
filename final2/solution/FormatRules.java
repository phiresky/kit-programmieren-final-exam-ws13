package final2;

/**
 * rules for the format of the input CSV strings, as regular expression
 * 
 * @author robin
 * @version 1
 */
public final class FormatRules {

    /**
     * document name, anything except empty
     */
    public static final String DOCUMENT_NAME = ".+";
    /**
     * tag name, must begin with a latin letter, then latin letters or numbers
     */
    public static final String TAG_NAME = "[a-zA-Z][a-zA-Z0-9]*";

    /** begins with 0-9 or -, then only decimals */
    public static final String INTEGER_TAG_VALUE = "[0-9-][0-9]*";
    /**
     * begins with a letter, then a letter, a number or spaces, unicode/umlaut
     * support not specified by assignment only latin characters (no unicode) is
     * supported as per assignment
     */
    public static final String MULTIVALUE_TAG_VALUE = "[a-zA-Z][a-zA-Z0-9 ]*";
    /** matches only an empty string */
    public static final String BOOLEAN_TAG_VALUE = "^$";

    private FormatRules() {
        // unused
    }
}
