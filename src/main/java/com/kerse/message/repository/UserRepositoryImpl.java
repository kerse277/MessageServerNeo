package com.kerse.message.repository;

import com.kerse.message.model.CustomUser;
import com.kerse.message.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserRepositoryImpl implements UserRepositoryCustom {

    private Neo4jOperations neo4jOperations;

    @Autowired
    public UserRepositoryImpl(Neo4jOperations neo4jTemplate) {
        this.neo4jOperations = neo4jTemplate;
    }



    @Override
    public List<User> findContact(List<String> listNumber) {
        String params="";
        Map<String, Object> param = new HashMap<>();

        for (String number : listNumber) {
            params += " n.phoneNumber='" + number + "' or";
        }

        String query = "MATCH (n:User) where" + params.substring(0,params.length()-3) + " return n";
        List<User> list = new ArrayList<>();

        for (User person : neo4jOperations.queryForObjects(User.class, query, param)) {

            list.add(person);
        }

        return list;
    }

}