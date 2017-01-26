package com.xtrade.streaming.impl.bolts.common;

/**
 * Created by jmedel on 8/9/16.
 */
public enum EventTypeStream {
    NOT_NORMAL("not-normal-stream");

    private String stream;

    EventTypeStream(String stream) {
        this.stream = stream;
    }

    public String getStream() {
        return stream;
    }
}
