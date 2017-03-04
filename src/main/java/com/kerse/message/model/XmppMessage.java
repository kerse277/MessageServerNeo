package com.kerse.message.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class XmppMessage {


    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String body;

}
