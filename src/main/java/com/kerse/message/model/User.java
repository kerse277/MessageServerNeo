package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "User")
@Accessors(chain = true)
public class User {

    @GraphId
    @Setter
    @Getter
    private Long id;

    @Getter
    @Setter
    private String uniqueID;

    @Setter
    @Getter
    private String profileName;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String deviceID;

    @Getter
    @Setter
    private String profilePhoto;

    @Getter
    @Setter
    private String profileText;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String token;
}
