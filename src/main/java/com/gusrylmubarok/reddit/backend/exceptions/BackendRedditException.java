package com.gusrylmubarok.reddit.backend.exceptions;

public class BackendRedditException extends RuntimeException {
    public BackendRedditException(String message, Exception exception) {
        super(message, exception);
    }
    public BackendRedditException(String message) {
        super(message);
    }
}
