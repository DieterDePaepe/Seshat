package com.github.dieterdepaepe.seshat.model.mongo;

import com.github.dieterdepaepe.seshat.model.Template;
import com.google.common.collect.Lists;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * MongoDB based implementation of {@link Template}.
 */
public class MongoTemplate implements Template {
    private JsonObject object;

    MongoTemplate(JsonObject object) {
        this.object = object;
    }

    public static MongoTemplate create(Template template) {
        if (template instanceof MongoTemplate)
            return (MongoTemplate) template;

        JsonObject object = new JsonObject();
        object.put("name", template.getName());
        List<JsonObject> fields = Lists.newArrayListWithCapacity(template.getFields().size());
        for (Field field : template.getFields())
            fields.add(MongoTemplateField.create(field).getObject());
        object.put("fields", fields);
        return new MongoTemplate(object);
    }

    @Override
    public String getId() {
        return object.getString("_id");
    }

    @Override
    public String getName() {
        return object.getString("name");
    }

    @Override
    public List<? extends Field> getFields() {
        return Lists.transform(
                object.getJsonArray("fields", new JsonArray()).getList(),
                MongoTemplateField::new
        );
    }

    void setId(String id) {
        object.put("_id", id);
    }

    JsonObject getObject() {
        return object;
    }
}
