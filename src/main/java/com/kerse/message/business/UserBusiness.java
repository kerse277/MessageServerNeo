package com.kerse.message.business;


import com.kerse.message.config.Config;
import com.kerse.message.model.*;
import com.kerse.message.repository.FriendRepository;
import com.kerse.message.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserBusiness {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;


    public User addUser(RegisterUserModel registerUserModel) {
        User user = registerUserModel.getUser();

        User user1 = userRepository.findByPhoneNumber(user.getPhoneNumber());

        if (user1 == null) {
            user.setUniqueID(UUID.randomUUID().toString())
                    .setProfileText("Happy Message!");

            user.setProfilePhoto("/resources/images/" + user.getUniqueID() + "/profile.jpg");
            userRepository.save(user);

            XmppRegister xmppRegister = new XmppRegister()
                    .setUser(user.getUniqueID())
                    .setHost("localhost")
                    .setPassword(user.getPassword());

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.postForObject("http://localhost:5281/api/register", xmppRegister, String.class);

            try {
                File imageFile = new File("//home//kerse//Desktop//MessageServerNeo//src//main//webapp//resources//images//profile.jpg");
                File mkdir = new File("//home//kerse//Desktop//MessageServerNeo//src//main//webapp//resources//images//" + user.getUniqueID());
                mkdir.mkdir();
                File filedir = new File("//home//kerse//Desktop//MessageServerNeo//src//main//webapp//resources//images//" + user.getUniqueID() + "//profile.jpg");

                FileUtils.copyFile(imageFile, filedir);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            List<User> contactUsers = new ArrayList<>();
            if (!registerUserModel.getContact().isEmpty())
                contactUsers = userRepository.findContact(registerUserModel.getContact());

            if (!contactUsers.isEmpty()) {
                for (User user2 : contactUsers) {

                    FriendRelationship friendRelationship = new FriendRelationship()
                            .setStartNode(user)
                            .setEndNode(user2);
                    friendRepository.save(friendRelationship);
                    FriendRelationship friendRelationship2 = new FriendRelationship()
                            .setStartNode(user2)
                            .setEndNode(user);
                    friendRepository.save(friendRelationship2);
                }
            }
            return user;
        } else {
            if (user1.getPassword().equals(user.getPassword())) {

                user1.setProfileName(user.getProfileName());
                userRepository.save(user1);
                return user1;

            } else

                return null;

        }

    }


    public Token login(LoginRequest loginRequest) {

        User user = userRepository.findByPhoneNumberAndPassword(loginRequest.getPhone(), loginRequest.getPassword());
        Token token = new Token();
        if (user != null) {
            String uniqueToken = UUID.randomUUID().toString();
            user.setToken(uniqueToken);
            token.setToken(uniqueToken);
            userRepository.save(user);
        } else {
            token.setToken("empty");
        }
        return token;
    }

    public List<CustomUser> userList(String token) {

        List<CustomUser> customUserList = new ArrayList<>();
        List<User> userList = friendRepository.findFriendAll(token);

        if (!userList.isEmpty()) {
            for (User user : userList) {
                CustomUser customUser = new CustomUser()
                        .setUniqueID(user.getUniqueID())
                        .setProfileText(user.getProfileText())
                        .setProfilePhoto(user.getProfilePhoto())
                        .setProfileName(user.getProfileName())
                        .setStatus(user.getStatus());
                customUserList.add(customUser);
            }
        }

        return customUserList;
    }

    public List<CustomUser> reuserList(UserListModel userListModel) {
        User user = userRepository.findByToken(userListModel.getToken());
        List<User> contactUsers = new ArrayList<>();
        List<User> userFriends = friendRepository.findFriendAll(userListModel.getToken());
        if (!userListModel.getContact().isEmpty()) {
            contactUsers = userRepository.findContact(userListModel.getContact());
            for (User user1 : userFriends) {
                contactUsers.remove(user1);
            }
        }
        if (!contactUsers.isEmpty()) {
            for (User user2 : contactUsers) {
                FriendRelationship friendRelationship = new FriendRelationship()
                        .setStartNode(user)
                        .setEndNode(user2);
                friendRepository.save(friendRelationship);
                FriendRelationship friendRelationship2 = new FriendRelationship()
                        .setStartNode(user2)
                        .setEndNode(user);
                friendRepository.save(friendRelationship2);
            }
        }

        return userList(userListModel.getToken());
    }

    public void active(String token) {

        User user = userRepository.findByToken(token);
        user.setStatus("active");
        userRepository.save(user);
    }

    public void passive(String token) {
        User user = userRepository.findByToken(token);

        Date date = new Date();
        date.setHours(date.getHours());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        user.setStatus(format.format(date));
        userRepository.save(user);
    }

    public ServerResponse getuserstatus(String uniqueID) {

        User user = userRepository.findByUniqueID(uniqueID);
        ServerResponse serverResponse = new ServerResponse()
                .setStatus(user.getStatus());
        return serverResponse;
    }

    public User updateProfilePhoto(Image image) {
        byte[] imageByteArray = org.apache.commons.codec.binary.Base64.decodeBase64(image.getBase64forImage());

        User user = userRepository.findByToken(image.getToken());
        String photoName = UUID.randomUUID().toString();
        String[] splitUrl = user.getProfilePhoto().split("/");
        String oldPhotoName = splitUrl[6];
        user.setProfilePhoto("/resources/images/" + user.getUniqueID() + "/" + photoName + ".jpg");

        userRepository.save(user);
        try {
            File deletePhoto = new File("//home//kerse//Desktop//MessageServerNeo//src//main//webapp//resources//images//" + user.getUniqueID() + "//" + oldPhotoName);
            deletePhoto.delete();

            FileOutputStream imageOutFile = new FileOutputStream("//home//kerse//Desktop//MessageServerNeo//src//main//webapp//resources//images//" + user.getUniqueID() + "//" + photoName + ".jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User updateProfile(User user) {
        User userUpdating = userRepository.findByToken(user.getToken());
        userUpdating.setProfileName(user.getProfileName())
                .setProfileText(user.getProfileText());
        userRepository.save(userUpdating);
        return userUpdating;
    }

    public CustomUser getUser(String token, String userId) {
        if (userRepository.findByToken(token) != null) {
            User user = userRepository.findByUniqueID(userId);
            CustomUser customUser = new CustomUser()
                    .setUniqueID(user.getUniqueID())
                    .setProfileText(user.getProfileText())
                    .setProfilePhoto(user.getProfilePhoto())
                    .setProfileName(user.getProfileName())
                    .setStatus(user.getStatus());
            return customUser;
        }else {
            return null;
        }
    }
}
