package com.kerse.message.repository;

import com.kerse.message.model.Message;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface MessageRepository extends GraphRepository<Message> {

    Message findByUniqueID(String uniqueID);

}
