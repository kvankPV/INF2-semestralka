package vynimky;

/**
 * Výnimka pre prázdny vstup.
 * @author quang
 */
public class PrazdnyVstupException extends IllegalArgumentException {
    public PrazdnyVstupException(String s) {
        super(s);
    }
}
