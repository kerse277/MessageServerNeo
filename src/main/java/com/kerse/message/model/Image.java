package com.kerse.message.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Image {

    @Getter
    @Setter
    private String base64forImage;

    @Getter
    @Setter
    private String token;

}
