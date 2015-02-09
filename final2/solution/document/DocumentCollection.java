package final2.document;

import final2.Util;
import final2.tag.Tag;

/**
 * A generic collection of documents
 * 
 * @author robin
 * @version 1
 */
public abstract class DocumentCollection implements Iterable<Document> {

    /**
     * get the total amount of times all the documents in this collection were
     * opened
     * 
     * @return the open count
     */
    public abstract int getOpenCount();

    /**
     * calculate the factor how uncertain it is which document the user wants to
     * access
     * 
     * @return the uncertainty factor
     */
    public double uncertainty() {
        double result = 0;
        for (Document d : this) {
            double p = d.getOpenCount() / (double) getOpenCount();
            if (p > 0) {
                result -= p * Math.log(p) / Math.log(2);
            } else {
                Util.handleError("open count of " + d.getName()
                        + " is not > 0.");
            }
        }
        return result;
    };

    /**
     * calculate the factor how uncertain it is which document the user wants to
     * access
     * 
     * @param tag
     *            the tag by which to split this collection
     * @return the {@link #uncertainty()} factor that remains after splitting
     *         this collection
     */
    public abstract double remainingUncertainty(Tag tag);

    /**
     * the expected gain of information when splitting this collection
     * 
     * @param tag
     *            the tag by this collection would be split
     * @return {@link #uncertainty()} - {@link #remainingUncertainty(Tag)}
     */
    public double exceptedInformationGain(Tag tag) {
        return uncertainty() - remainingUncertainty(tag);
    }

    /**
     * print all documents in this collection with the specified prefix
     * 
     * @param prefix
     *            the "folder" which would contain this collection
     * @return string representation
     */
    public abstract String toString(String prefix);

}
