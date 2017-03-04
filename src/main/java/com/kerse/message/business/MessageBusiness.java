package com.kerse.message.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerse.message.config.Config;
import com.kerse.message.model.Message;
import com.kerse.message.model.ServerResponse;
import com.kerse.message.model.User;
import com.kerse.message.model.XmppMessage;
import com.kerse.message.repository.MessageRepository;
import com.kerse.message.repository.UserRepository;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageBusiness {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public ServerResponse sendMessageXmpp(Message message) {

        User senderUser = userRepository.findByToken(message.getSenderToken());
        User receiverUser = userRepository.findByUniqueID(message.getReceiverID());
        message.setSenderID(senderUser.getUniqueID());
        message.setSenderToken(null);

        ServerResponse serverResponse = new ServerResponse();
        if (receiverUser == null) {
            serverResponse.setStatus("NMR");
            return serverResponse;
        }

        if (senderUser == null) {
            serverResponse.setStatus("NMR");
            return serverResponse;
        }

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        message.setSendDate(format.format(date));

        ObjectMapper objectMapper = new ObjectMapper();
        XmppMessage xmppMessage = new XmppMessage();
        try {
            xmppMessage.setType("chat")
                    .setFrom(senderUser.getUniqueID() + "@localhost")
                    .setTo(receiverUser.getUniqueID() + "@localhost")
                    .setSubject("message")
                    .setBody(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.postForObject("http://localhost:5281/api/send_message", xmppMessage, String.class);

        serverResponse.setStatus(format.format(date));
        return serverResponse;

    }

    public ServerResponse sendMessage(Message message) {


        ServerResponse serverResponse = new ServerResponse();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(
                "https://fcm.googleapis.com/fcm/send");

        StringEntity input = null;

        ObjectMapper mapper = new ObjectMapper();
        User receiverUser = userRepository.findByUniqueID(message.getReceiverID());
        if (receiverUser == null) {
            serverResponse.setStatus("NMR");
            return serverResponse;
        }
        String regID = receiverUser.getDeviceID();

        User user = userRepository.findByToken(message.getSenderToken());
        if (user == null) {
            serverResponse.setStatus("NMR");
            return serverResponse;
        }

        message.setSenderID(user.getUniqueID());

        message.setSenderToken(null);

        Date date = new Date();
        date.setHours(date.getHours());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        message.setSendDate(format.format(date));

        // messageRepository.save(message);

        String customMessage = null;
        try {

            customMessage = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            input = new StringEntity("{\n" +
                    "    \"to\": \"" + regID + "\"" +
                    " \n" +
                    "    \"data\": " + customMessage +
                    "}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        input.setContentType("application/json");
        postRequest.setHeader("Authorization", "key=" + Config.FCM_KEY);
        postRequest.setEntity(input);


        try {
            httpClient.execute(postRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverResponse.setStatus(format.format(date));
        return serverResponse;
    }



}
