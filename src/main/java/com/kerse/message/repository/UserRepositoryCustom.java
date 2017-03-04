package com.kerse.message.repository;


import com.kerse.message.model.User;

import java.util.List;

/**
 * Created by kerse on 19.07.2016.
 */
public interface UserRepositoryCustom {


    List<User> findContact(List<String> listNumber);
}
