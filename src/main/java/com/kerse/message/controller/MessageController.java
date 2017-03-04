package com.kerse.message.controller;

import com.kerse.message.business.MessageBusiness;
import com.kerse.message.model.Message;
import com.kerse.message.model.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLSocketFactory;
import java.util.List;

@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageBusiness messageBusiness;

    @PostMapping("sendmessage")
    @ResponseBody
    public ServerResponse sendMessage(@RequestBody Message message){
        return messageBusiness.sendMessage(message);
    }


    @PostMapping("sendmessagexmpp")
    @ResponseBody
    public ServerResponse sendMessageXmpp(@RequestBody Message message){
        return messageBusiness.sendMessageXmpp(message);
    }
    
}
