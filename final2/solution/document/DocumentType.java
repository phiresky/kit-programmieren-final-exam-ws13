package final2.document;

import final2.Util;

public enum DocumentType {
    /** just what you would expect */
    IMAGE, AUDIO, VIDEO, TEXT, PROGRAM, INVALID;

    /**
     * parse a string into a document type, directing errors to
     * {@link Util#handleError(String)} if necessary
     * 
     * @param documentTypeString
     *            the string to parse
     * @return the document type corresponding to the string
     */
    public static DocumentType parse(String documentTypeString) {
        if (!documentTypeString.equals(documentTypeString.toLowerCase())) {
            // document type must be lower case
            // https://ilias.studium.kit.edu
            // /ilias.php?ref_id=305504&cmdClass=ilobjforumgui&thr_pk=41893
            // &cmd=viewThread&cmdNode=ed:5v&baseClass=ilRepositoryGUI
            Util.handleError("Invalid document type " + documentTypeString
                    + ", must be lower case");
        }
        try {
            return DocumentType.valueOf(documentTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            Util.handleError("Invalid document type " + documentTypeString);
            return INVALID;
        } catch (NullPointerException e) {
            Util.handleError("Invalid document type " + documentTypeString);
            return INVALID;
        }

    }
}
