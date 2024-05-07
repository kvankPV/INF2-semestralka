package vynimky;

import java.io.FileNotFoundException;

/**
 * Výnimka pre nenašiel súbor.
 *  @author quang
 */
public class NenasielSuborException extends FileNotFoundException {
    public NenasielSuborException(String s) {
        super(s);
    }
}
