package com.github.dieterdepaepe.seshat.model.pojo;

import com.github.dieterdepaepe.seshat.model.Template;

import java.util.List;

/**
 * Simple implementation of {@link Template}.
 */
public class TemplateImpl implements Template {
    private Object id;
    private String name;
    private List<TemplateFieldImpl> fields;

    public TemplateImpl(Object id, String name, List<TemplateFieldImpl> fields) {
        this.id = id;
        this.name = name;
        this.fields = fields;
    }

    public TemplateImpl() {
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TemplateFieldImpl> getFields() {
        return fields;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFields(List<TemplateFieldImpl> fields) {
        this.fields = fields;
    }
}
