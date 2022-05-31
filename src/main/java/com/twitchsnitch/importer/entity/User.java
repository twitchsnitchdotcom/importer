package com.twitchsnitch.importer.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @Property("icon_filename")
    private String iconFilename;


}
