package final2.tag;

import final2.Util;
import final2.document.Document;
import final2.document.DocumentType;
import final2.tag.value.TagValue;

/**
 * Represent rules for converting integer tags to named tags i.e. size < 100 =>
 * small size < 200 => medium size > 200 => large
 * 
 * @author robin
 * @version 1
 */
public final class IntegerTagRule {

    /**
     * the default set of rules to use for new documents
     * 
     * @formatter:off
     */
    private static IntegerTagRule[] rules = {
            new IntegerTagRule(DocumentType.IMAGE, "size", "imagesize",
                    new int[] {10000, 40000, 800000}, 
                    new String[] {"Icon", "Small", "Medium", "Large"}),
                    
            new IntegerTagRule(DocumentType.AUDIO, "length", "audiolength",
                    new int[] {10, 60, 300}, 
                    new String[] {"Sample", "Short", "Normal", "Long"}),
                    
            new IntegerTagRule(DocumentType.TEXT, "words", "textlength",
                    new int[] {100, 1000}, 
                    new String[] {"Short", "Medium", "Long"}),
                    
            new IntegerTagRule(DocumentType.VIDEO, "length", "videolength",
                    new int[] {300, 3600, 7200}, 
                    new String[] {"Clip", "Short", "Movie", "Long"})};
    // @formatter:on

    /**
     * if true parsing will abort when a input tag does not have the expected
     * tag type
     */
    private static final boolean ABORT_ON_TAGTYPE_ERROR = false;
    private final DocumentType documentType;
    private final String inputTag;
    private final String replacementTag;
    private final int[] bounds;
    private final String[] names;

    private IntegerTagRule(DocumentType documentType, String inputTag,
            String replacementTag, int[] bounds, String[] names) {
        if (bounds.length != names.length - 1) {
            Util.handleError("invalid IntegerTagRule");
        }
        this.documentType = documentType;
        this.inputTag = inputTag;
        this.replacementTag = replacementTag;
        this.bounds = bounds;
        this.names = names;

    }

    /**
     * apply this rule to a document
     * 
     * @param doc
     *            the document to apply the rule to
     */
    public void apply(Document doc) {
        TagMap tags = doc.getTags();
        if (doc.getFiletype() == documentType) {
            Tag tag = tags.getManager().get(inputTag);
            if (tag != null) {
                if (tag.getType() != TagType.IntegerTag) {
                    if (ABORT_ON_TAGTYPE_ERROR) {
                        Util.handleError("invalid tag type " + tag.getType()
                                + " for tag " + inputTag);
                    }
                } else {
                    TagValue input = tags.remove(tag);

                    if (input != null) {
                        try {
                            String output = names[0];
                            int len = Integer.parseInt(input.toString());
                            for (int i = 0; i < bounds.length
                                    && len >= bounds[i]; i++) {
                                output = names[i + 1];
                            }
                            tags.set(replacementTag, output,
                                    TagType.MultivalueTag);
                        } catch (NumberFormatException e) {
                            Util.handleError(String.format(
                                    "%s has invalid %s: %s", doc.getName(),
                                    inputTag, input.toString()));
                        }
                    }
                }
            }
        }
    }

    /**
     * apply all default rules from {@link #rules} to doc
     * 
     * @param doc
     *            the document
     */
    public static void applyAll(Document doc) {
        for (IntegerTagRule rule : rules) {
            rule.apply(doc);
        }
    }
}
