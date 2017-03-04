package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class CustomUser {

    @Getter
    @Setter
    private String uniqueID;

    @Getter
    @Setter
    private String phoneNumber;

    @Setter
    @Getter
    private String profileName;

    @Getter
    @Setter
    private String profilePhoto;

    @Getter
    @Setter
    private String profileText;

    @Getter
    @Setter
    private String status;



}
