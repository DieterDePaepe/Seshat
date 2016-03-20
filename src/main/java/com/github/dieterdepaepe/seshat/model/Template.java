package com.github.dieterdepaepe.seshat.model;

import org.bson.types.ObjectId;

/**
 * Defines the schema of a data repository.
 *
 * @author Dieter De Paepe
 */
public class Template {
    private ObjectId id;
    private String name;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
