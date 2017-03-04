package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.LocalTime;
import java.util.Date;

@NodeEntity(label = "Message")
@Accessors(chain = true)
public class Message {

    @GraphId
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String uniqueID;

    @Getter
    @Setter
    private String messageText;

    @Getter
    @Setter
    private String senderToken;

    @Getter
    @Setter
    private String senderID;

    @Getter
    @Setter
    private String receiverID;

    @Getter
    @Setter
    private String sendDate;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String stripe;

}
