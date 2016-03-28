package com.github.dieterdepaepe.seshat.model.pojo;

import com.github.dieterdepaepe.seshat.model.DataType;
import com.github.dieterdepaepe.seshat.model.Restriction;
import com.github.dieterdepaepe.seshat.model.Template;

import java.util.List;

/**
 * Simple implementation of {@link com.github.dieterdepaepe.seshat.model.Template.Field}
 */
public class TemplateFieldImpl implements Template.Field {
    private String name;
    private DataType dataType;

    public TemplateFieldImpl(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public TemplateFieldImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public List<Restriction> getRestrictions() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
