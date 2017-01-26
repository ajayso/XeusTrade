package com.xtrade.streaming.impl.bolts.common;

/**
 * Created by jmedel on 8/9/16.
 */
public enum EventType {
    NORMAL("Normal");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
