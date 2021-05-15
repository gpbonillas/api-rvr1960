package org.bible.api.rvr1960.exception;

public class BibleException extends Exception {
    public BibleException () {
        super();
    }

    public BibleException (String message) {
        super(message);
    }

    public BibleException (String message, Throwable cause) {
        super(message, cause);
    }
}
