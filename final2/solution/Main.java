package final2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import final2.document.DocumentCollection;
import final2.document.FlatDocumentCollection;
import final2.tag.TagManager;

/**
 * The main class used for loading a list of documents into the tagging system,
 * sorting them and outputting the optimized "file system"
 * 
 * @author robin
 * @version 1
 * 
 */
public final class Main {

    /**
     * if true, all errors will result in the application exiting
     */
    public static final boolean ERRORS_FATAL = true;

    private Main() {
        // unused
    }

    /**
     * Build an optimal file tree for the specified list of documents
     * 
     * @param args
     *            filename of the input file
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            String filename = args[0];
            try {
                File file = new File(filename);
                if (file.isFile()) { // also handles file exists
                    parseInput(new FileInputStream(file));
                } else {
                    Util.handleError(filename + " is not a file.");
                }
            } catch (FileNotFoundException e) { // should already be caught by
                                                // file.isFile()
                Util.handleError("Could not open " + filename + ": "
                        + e.getMessage());
            }
        } else {
            Util.handleError("Specify filename of input file as argument.");
        }
    }

    private static void parseInput(InputStream inputStream) {
        TagManager tags = new TagManager();
        DocumentCSVParser parser = new DocumentCSVParser(tags);
        FlatDocumentCollection documents = parser.parse(inputStream);

        DocumentCollection split = documents.recursiveSplit(tags, "/");
        System.out.println("---");
        System.out.print(split.toString("/"));
    }
}
