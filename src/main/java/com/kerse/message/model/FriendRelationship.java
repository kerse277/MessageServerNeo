package com.kerse.message.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "FRIEND")
@Accessors(chain = true)
public class FriendRelationship {

    @GraphId
    private Long id;

    @Getter
    @Setter
    @StartNode
    private User startNode;

    @Getter
    @Setter
    @EndNode
    private User endNode;


}
