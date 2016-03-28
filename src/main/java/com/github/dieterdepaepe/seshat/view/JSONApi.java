package com.github.dieterdepaepe.seshat.view;

import com.github.dieterdepaepe.seshat.model.DataType;
import com.github.dieterdepaepe.seshat.model.Template;
import com.github.dieterdepaepe.seshat.model.pojo.TemplateFieldImpl;
import com.github.dieterdepaepe.seshat.model.pojo.TemplateImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for dealing with JSON API related conversions.
 */
public class JSONApi {
    public static final String MIME_TYPE = "application/vnd.api+json";

    public JsonObject fromError(Integer httpCode, String title) {
        JsonObject errorObject = new JsonObject();
        if (httpCode != null)
            errorObject.put("status", httpCode);
        if (title != null)
            errorObject.put("title", title);

        JsonObject result = new JsonObject();
        result.put("errors", new JsonArray().add(errorObject));
        return result;
    }

    public JsonObject fromData(Iterable<JsonObject> objects) {
        JsonObject result = new JsonObject();
        JsonArray data = new JsonArray();
        for (JsonObject object : objects) {
            data.add(object);
        }
        result.put("data", data);
        return result;
    }

    public JsonObject fromData(JsonObject object) {
        JsonObject result = new JsonObject();
        result.put("data", object);
        return result;
    }

    public JsonObject convertTemplate(Template template) {
        JsonObject object = new JsonObject();
        object.put("type", "template");
        object.put("id", String.valueOf(template.getId()));
        JsonObject attributes = new JsonObject();
        object.put("attributes", attributes);
        attributes.put("name", template.getName());

        JsonArray fields = new JsonArray();
        attributes.put("fields", fields);
        for (Template.Field field : template.getFields()) {
            fields.add(
                    new JsonObject()
                    .put("name", field.getName())
                    .put("datatype", field.getDataType())
            );
        }
        return object;
    }

    public Template parseJson(JsonObject jsonAPI) throws IllegalArgumentException {
        JsonObject data = jsonAPI.getJsonObject("data");
        if (data == null)
            throw new IllegalArgumentException("Missing data field");
        String id = data.getString("id");
        String type = data.getString("type");
        if (!"template".equals(type))
            throw new IllegalArgumentException("Invalid type: " + type);

        JsonObject attributes = data.getJsonObject("attributes");
        if (attributes == null)
            throw new IllegalArgumentException("Missing attributes");
        String name = attributes.getString("name");
        if (name == null)
            throw new IllegalArgumentException("Missing name field");
        JsonArray fields = attributes.getJsonArray("fields");
        if (fields == null)
            throw new IllegalArgumentException("Missing fields field");

        List<TemplateFieldImpl> parsedFields = new ArrayList<>();
        for (Object field : fields) {
            if (!(field instanceof JsonObject))
                throw new IllegalArgumentException("Invalid input in field: " + field);
            JsonObject fieldObj = (JsonObject) field;
            String fieldName = fieldObj.getString("name");
            if (fieldName == null)
                throw new IllegalArgumentException("Missing field name");
            String fieldDatatypeStr = fieldObj.getString("datatype");
            if (fieldDatatypeStr == null)
                throw new IllegalArgumentException("Missing field datatype");
            DataType fieldDatatype = DataType.valueOf(fieldDatatypeStr);
            parsedFields.add(new TemplateFieldImpl(fieldName, fieldDatatype));
        }


        return new TemplateImpl(id, name, parsedFields);
    }
}
