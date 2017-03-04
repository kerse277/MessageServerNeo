package com.kerse.message.repository;

import com.kerse.message.model.FriendRelationship;
import com.kerse.message.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kerse on 21.06.2016.
 */
public interface FriendRepository extends GraphRepository<FriendRelationship> {

    @Query("MATCH (:User{token: {token} })-[:FRIEND]->(p) return p")
    List<User> findFriendAll(@Param("token") String token);

}