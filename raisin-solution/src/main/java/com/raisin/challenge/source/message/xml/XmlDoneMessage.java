package com.raisin.challenge.source.message.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "msg")
public class XmlDoneMessage {

    @JacksonXmlProperty(localName = "done")
    private String done;

    public XmlDoneMessage() {

    }

    public void setDone(String done) {
        this.done = done == null ? "" : done;
    }

    @Override
    public String toString() {
        return "XmlDoneMessage [done=" + done + "]";
    }
}
