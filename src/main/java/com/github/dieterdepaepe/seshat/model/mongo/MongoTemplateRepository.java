package com.github.dieterdepaepe.seshat.model.mongo;

import com.github.dieterdepaepe.seshat.model.Template;
import com.github.dieterdepaepe.seshat.model.TemplateRepository;
import com.google.common.collect.Lists;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.function.Function;

/**
 * MongoDB based implementation of {@link TemplateRepository}.
 */
public class MongoTemplateRepository implements TemplateRepository {
    private static final String TABLE = "Templates";
    private MongoClient mongo;

    public MongoTemplateRepository(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Override
    public void getTemplates(Handler<AsyncResult<List<? extends Template>>> handler) {
        Function<List<JsonObject>, List<? extends Template>> converter =
                list -> Lists.transform(list, MongoTemplate::new);
        mongo.find(TABLE, new JsonObject(), wrap(handler, converter));
    }

    @Override
    public void addTemplate(Template newTemplate, Handler<AsyncResult<Template>> handler) {
        MongoTemplate mongoTemplate = MongoTemplate.create(newTemplate);
        mongo.insert(TABLE, mongoTemplate.getObject(), wrap(handler, id -> {
            mongoTemplate.setId(id);
            return mongoTemplate;
        }));
    }

    @Override
    public void getTemplate(Object id, Handler<AsyncResult<Template>> handler) {
        JsonObject query = new JsonObject().put("_id", id);
        mongo.findOne(TABLE, query, null, wrap(handler, json -> json == null ? null : new MongoTemplate(json)));
    }

    private <F, T> Handler<AsyncResult<F>> wrap(Handler<AsyncResult<T>> handler, java.util.function.Function<F, T> converter) {
        return event -> handler.handle(new AsyncResult<T>() {

            @Override
            public T result() {
                return converter.apply(event.result());
            }

            @Override
            public Throwable cause() {
                return event.cause();
            }

            @Override
            public boolean succeeded() {
                return event.succeeded();
            }

            @Override
            public boolean failed() {
                return event.failed();
            }
        });
    }
}
