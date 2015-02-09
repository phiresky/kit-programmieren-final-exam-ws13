package final2;

import java.io.InputStream;
import java.util.Scanner;

import final2.document.Document;
import final2.document.FlatDocumentCollection;
import final2.tag.TagManager;
import final2.tag.TagMap;
import final2.tag.TagType;
import final2.tag.value.BooleanTagValue;

/**
 * Static methods for parsing CSV lists of documents
 * 
 * @author robin
 * @version 1
 */
public final class DocumentCSVParser {

    private final TagManager tagMan;

    /**
     * create a new DocumentCSV parser using the specified tag manager
     * 
     * @param tagMan
     *            the tag manager to put the tags into
     */
    public DocumentCSVParser(TagManager tagMan) {
        this.tagMan = tagMan;
    }

    /**
     * parse a CSV file in the format specified by ass2 pdf
     * 
     * @param is
     *            the input stream to read from
     * @return a flat document collection of the documents in the csv
     */
    public FlatDocumentCollection parse(InputStream is) {
        FlatDocumentCollection documents = new FlatDocumentCollection();
        Scanner scanner = new Scanner(is);
        int lineNumber = 1;
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",", -1);
            if (line.length < 3) {
                Util.handleError("not enough arguments on line " + lineNumber
                        + ", skipping");
            } else {
                documents.add(parseDocumentLine(line, lineNumber));
            }
            lineNumber++;
        }
        scanner.close();
        return documents;
    }

    /**
     * parse a document line
     * 
     * @param line
     *            the array of elements in the line, at least 3 elements
     * @param lineNumber
     *            the line number for more meaningful error messages
     * @return the parsed document object
     */
    private Document parseDocumentLine(String[] line, int lineNumber) {
        String documentName = line[0];
        if (!documentName.matches(FormatRules.DOCUMENT_NAME)) {
            Util.handleError("invalid document name on line " + lineNumber);
        }
        String documentType = line[1];
        int openCount = 0;
        try {
            openCount = Integer.parseInt(line[2]);
        } catch (NumberFormatException e) {
            openCount = -1;
        }
        if (openCount < 0) {
            Util.handleError("invalid open count on line " + lineNumber);
        }
        TagMap tags = new TagMap(tagMan);
        for (int i = 3; i < line.length; i++) {
            parseTag(tags, line[i], documentName, lineNumber);
        }
        return new Document(documentName, documentType, openCount, tags);
    }

    /**
     * parse a single tag in the form "tag=value"
     * 
     * @param tagMap
     *            the tag into which the tag will be written
     * @param unparsedString
     *            the unparsed tag string ("foo=bar")
     * @param documentName
     *            the filename of the current document for more meaningful error
     *            messages
     * @param lineNumber
     *            the line number of the current document for more meaningful
     *            error messages
     */
    private void parseTag(TagMap tagMap, String unparsedString,
            String documentName, int lineNumber) {
        if (unparsedString.length() == 0) {
            Util.handleError(String.format(
                    "Empty tag for document %s in line %d", documentName,
                    lineNumber));
        }
        String[] tagSplit = unparsedString.split("=", -1);

        String tagName = tagSplit[0];
        if (!tagName.matches(FormatRules.TAG_NAME)) {
            Util.handleError(String.format(
                    "Invalid tag name %s for document %s in line %d", tagName,
                    documentName, lineNumber));
        }
        String tagValue = null;
        TagType tagType = TagType.Invalid;
        if (tagSplit.length == 1) {
            tagValue = BooleanTagValue.DEFINED;
            tagType = TagType.BooleanTag;
        } else if (tagSplit.length == 2) {
            tagValue = tagSplit[1];
            if (tagValue.length() > 0) { // format "tag=" not allowed
                tagType = TagType.inferFromValueString(tagValue);
            } else {
                tagType = TagType.Invalid;
            }
        } else {
            tagType = TagType.Invalid;
        }
        if (tagType == TagType.Invalid) {
            Util.handleError(String.format(
                    "Invalid tag type for '%s' for document %s in line %d",
                    unparsedString, documentName, lineNumber));
        } else {
            tagMap.set(tagName, tagValue, tagType);
        }
    }
}
