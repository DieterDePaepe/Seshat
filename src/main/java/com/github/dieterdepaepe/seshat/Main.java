package com.github.dieterdepaepe.seshat;

import com.github.dieterdepaepe.seshat.controller.TemplateController;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.TemplateHandler;

/**
 * Created by Xorpion on 20/03/2016.
 * TODO: toString, equals/hash, threadsafe?
 *
 * @author Dieter De Paepe
 */
public class Main extends AbstractVerticle{
    // See https://docs.mongodb.org/manual/reference/connection-string/
    private static final String MONGO_CONN_STRING = "mongodb://192.168.99.100";

    @Override
    public void start(final Future<Void> fut) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        MongoClient mongo = MongoClient.createShared(vertx,
                new JsonObject().put("connection_string", MONGO_CONN_STRING));
        TemplateController templates = new TemplateController(mongo);
        router.get("/templates").handler(templates::listTemplates);
        router.get("/templates/:templateID").handler(templates::getTemplate);
        router.post("/templates").handler(templates::addTemplate);

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(8080, result -> {
                    if (result.succeeded())
                        fut.complete();
                    else
                        fut.fail(result.cause());
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions());

        vertx.deployVerticle(new Main());
    }
}
