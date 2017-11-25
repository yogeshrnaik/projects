package com.raisin.challenge.source.message.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.challenge.source.message.SourceMessage;
import com.raisin.challenge.source.message.xml.XmlDoneMessage;
import com.raisin.challenge.source.message.xml.XmlIdMessage;

public class XmlMessageParser extends MessageParser {

    public XmlMessageParser() {
        super(new XmlMapper());
    }

    public SourceMessage parse(String source, String message) {
        XmlIdMessage idMsg = parse(message, XmlIdMessage.class);
        if (idMsg != null)
            return new SourceMessage(source, idMsg.getMessageId().getValue());

        XmlDoneMessage doneMsg = parse(message, XmlDoneMessage.class);
        if (doneMsg != null)
            return new SourceMessage(source, true);

        return null;
    }

}
