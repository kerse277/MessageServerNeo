package com.kerse.message.repository;

import com.kerse.message.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface UserRepository extends GraphRepository<User>, UserRepositoryCustom {

    User findByUniqueID(String uniqueID);

    User findByToken(String token);

    User findByPhoneNumberAndPassword(String phoneNumber,String password);

    User findByPhoneNumber(String phoneNumber);

}
