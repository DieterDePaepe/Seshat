package com.github.dieterdepaepe.seshat.controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * Class containing all controller classes related to {@code Template}s.
 *
 * @author Dieter De Paepe
 */
public class TemplateController {
    private static final String TABLE = "templates";

    private MongoClient mongo;

    public TemplateController(MongoClient mongo) {
        this.mongo = mongo;
    }

    public void listTemplates(RoutingContext routingContext) {
        mongo.find(TABLE, new JsonObject(), lookup -> {
            if (lookup.failed()) {
                routingContext.fail(lookup.cause());
                return;
            }

            JsonArray json = new JsonArray();
            for (JsonObject entry : lookup.result()) {
                entry.put("id", entry.getValue("_id"));
                entry.remove("_id");
                json.add(entry);
            }

            routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            routingContext.response().end(json.encode());
        });
    }

    public void addTemplate(RoutingContext routingContext) {
        JsonObject newTemplate = new JsonObject();
        newTemplate.put("name", routingContext.request().getFormAttribute("name"));

        mongo.insert(TABLE, newTemplate, insert -> {
            if (insert.failed()) {
                routingContext.fail(insert.cause());
                return;
            }

            newTemplate.remove("_id"); //Automatically added by insert
            newTemplate.put("id", insert.result());
            routingContext.response().setStatusCode(HttpResponseStatus.CREATED.code());
            routingContext.response().end(newTemplate.encode());
        });
    }

    public void getTemplate(RoutingContext routingContext) {
        JsonObject query = new JsonObject().put("_id", routingContext.request().getParam("templateID"));
        mongo.findOne(TABLE, query, new JsonObject(), result -> {
            if (result.failed()) {
                routingContext.fail(result.cause());
                return;
            }

            if (result.result() == null) {
                routingContext.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code());
                routingContext.response().end();
                return;
            }

            routingContext.response().end(result.result().encode());
        });
    }
}
