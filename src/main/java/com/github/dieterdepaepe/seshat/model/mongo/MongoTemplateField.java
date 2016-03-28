package com.github.dieterdepaepe.seshat.model.mongo;

import com.github.dieterdepaepe.seshat.model.DataType;
import com.github.dieterdepaepe.seshat.model.Restriction;
import com.github.dieterdepaepe.seshat.model.Template;
import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.List;

/**
 * MongoDB based implementation of {@link com.github.dieterdepaepe.seshat.model.Template.Field}
 */
public class MongoTemplateField implements Template.Field {
    private JsonObject object;

    static MongoTemplateField create(Template.Field field) {
        if (field instanceof MongoTemplateField)
            return (MongoTemplateField) field;

        JsonObject object = new JsonObject();
        object.put("name", field.getName());
        object.put("datatype", field.getDataType());
        return new MongoTemplateField(object);
    }

    MongoTemplateField(JsonObject object) {
        this.object = object;
    }

    @Override
    public String getName() {
        return object.getString("name");
    }

    @Override
    public DataType getDataType() {
        String datatypeString = object.getString("datatype");
        if (datatypeString == null)
            return null;

        return DataType.valueOf(datatypeString);
    }

    @Override
    public List<Restriction> getRestrictions() {
        return Collections.emptyList();
    }

    public JsonObject getObject() {
        return object;
    }
}
