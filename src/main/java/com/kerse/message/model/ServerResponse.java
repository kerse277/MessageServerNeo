package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Accessors(chain = true)
public class ServerResponse {

    @Getter
    @Setter
    private String status;
}
