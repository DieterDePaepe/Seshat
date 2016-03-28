package com.github.dieterdepaepe.seshat.model;

import java.util.List;

/**
 * Defines the schema of a data repository.
 *
 * @author Dieter De Paepe
 */
public interface Template {
    Object getId();
    String getName();
    List<? extends Field> getFields();

    interface Field {
        String getName();
        DataType getDataType();
        List<Restriction> getRestrictions();
    }
}
