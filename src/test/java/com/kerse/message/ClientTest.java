package com.kerse.message;

import com.kerse.message.model.*;
import com.kerse.message.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kerse on 23.10.2016.
 */
public class ClientTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void addUserTest(){
        RestTemplate restTemplate=new RestTemplate();

        User user= new User();
        user.setPhoneNumber("+90(553) 592-1248")
                .setProfileName("Fatma Keleş");
        RegisterUserModel registerUserModel = new RegisterUserModel()
                .setUser(user)
                .setContact(new ArrayList<>());

                User serverResponse=restTemplate.postForObject("http://localhost:9091/user/adduser",registerUserModel,User.class);
        System.out.println(serverResponse.getProfileName());
    }

    @Test
    public void loginTest(){
        RestTemplate restTemplate=new RestTemplate();
        LoginRequest loginRequest = new LoginRequest()

                .setPhone("+90(555) 637-6510");
        Token token=restTemplate.postForObject("http://localhost:9091/user/login",loginRequest,Token.class);
        System.out.println(token.getToken());
    }

    @Test
    public void sendMessage(){
        RestTemplate restTemplate=new RestTemplate();


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Message message = new Message()
                .setReceiverID("4271b34c-6c8a-4d66-b20f-632b8c16e12e")
                .setMessageText("😝😛😙🤑😙😒")
                .setSenderToken("17081c62-6eba-4361-90c1-44ba3b438902")
                .setUniqueID(UUID.randomUUID().toString())
                .setSendDate(format.format(date))
                .setType("1")
                .setStatus("0");
        String text=restTemplate.postForObject("http://localhost:9091/message/sendmessage",message,String.class);
         //String text2=restTemplate.postForObject("http://localhost:9091/message/sendmessage",message,String.class);
        System.out.println(text);
    }

    @Test
    public void sendMessageXmpp(){
        RestTemplate restTemplate=new RestTemplate();


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Message message = new Message()
                .setReceiverID("abb3d530-6c44-42a1-b113-44580cf195cb")
                .setMessageText("Yazı, sıklıkla, bir dildeki sözleri temsil eden semboller sistemi olarak tarif edilir. Sözler kalıcı değilken yazı somut bir varlıktır ve sonsuza kadar muhafaza edilebilir. Hem konuşma hem de yazma bir dilin yapısal özelliklerine bağımlıdır. Bunun bir sonucu olarak belirli bir dildeki yazı, o dilin oral (konuşulan) formunun yapısal özelliklerine aşina olmayan bir kimse tarafından okunamaz.[1] Bununla birlikte yazı sadece sözlerin kağıda dökülmesi değildir; bazen dilin edebî veya bilimsel kullanımlarından doğan çeşitli özel formlarının da sembole dönüştürülmesidir ki bunlar her zaman sözlü olarak ifade edilemeyebilirler.")
                .setSenderToken("1c9024c4-257b-4889-8948-1a8ef7b80a57")
                .setUniqueID(UUID.randomUUID().toString())
                .setSendDate(format.format(date))
                .setType("1")
                .setStatus("0");
        String text=restTemplate.postForObject("http://localhost:9091/message/sendmessagexmpp",message,String.class);
        //String text2=restTemplate.postForObject("http://localhost:9091/message/sendmessage",message,String.class);
        System.out.println(text);
    }

    @Test
    public void activeTest(){
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> map=new HashMap<>();
        map.put("token","56bc0ce9-b435-486f-9338-7ab9306c430f");
        restTemplate.getForObject("http://localhost:9091/user/active?token=56bc0ce9-b435-486f-9338-7ab9306c430f",Void.class);
        //restTemplate.getForObject("http://localhost:9091/user/getuserstatus?uniqueid=0aa352b2-816d-4cd9-9647-12fbbe38bf58",Void.class);

    }

    @Test
    public void imageTest(){
        try {
            File imageFile = new File("//home//kerse//Desktop//Message//MessageServerNeo//src//main//webapp//resources//images//profile2.jpg");
            File mkdir = new File("//home//kerse//Desktop//Message//MessageServerNeo//src//main//webapp//resources//images//ddd" );

            mkdir.mkdir();
            File filedir = new File("//home//kerse//Desktop//Message//MessageServerNeo//src//main//webapp//resources//images//ddd//profile2.jpg" );

            FileUtils.copyFile(imageFile, filedir);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void FCMtest(){

    }
}
