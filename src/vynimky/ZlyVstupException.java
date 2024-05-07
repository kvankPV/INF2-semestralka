package vynimky;

/**
 * Výnimka pre zlý vstup.
 *  @author quang
 */
public class ZlyVstupException extends IllegalArgumentException {
    public ZlyVstupException(String s) {
        super(s);
    }
}
