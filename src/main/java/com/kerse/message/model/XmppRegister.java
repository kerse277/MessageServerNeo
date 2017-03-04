package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class XmppRegister {

    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private String password;


}
