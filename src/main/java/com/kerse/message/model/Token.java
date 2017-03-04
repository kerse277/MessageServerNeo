package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Token {

    @Getter
    @Setter
    private String token;
}
