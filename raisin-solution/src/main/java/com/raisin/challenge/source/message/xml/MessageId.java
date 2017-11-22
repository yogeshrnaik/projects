package com.raisin.challenge.source.message.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MessageId {

    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

    public MessageId() {
    }

    public MessageId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
