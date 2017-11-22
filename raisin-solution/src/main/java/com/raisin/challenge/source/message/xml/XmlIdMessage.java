package com.raisin.challenge.source.message.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "msg")
public class XmlIdMessage {

    @JacksonXmlElementWrapper(localName = "id")
    private MessageId messageId;

    public XmlIdMessage() {
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public void setMessageId(MessageId id) {
        this.messageId = id;
    }

    @Override
    public String toString() {
        return "XmlIdMessage [messageId=" + messageId + "]";
    }
}
