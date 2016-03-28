package com.github.dieterdepaepe.seshat.controller;

import com.github.dieterdepaepe.seshat.model.Template;
import com.github.dieterdepaepe.seshat.model.TemplateRepository;
import com.github.dieterdepaepe.seshat.view.JSONApi;
import com.google.common.collect.Lists;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

/**
 * Class containing all controller classes related to {@code Template}s.
 *
 * @author Dieter De Paepe
 */
public class TemplateController {
    private TemplateRepository templateRepository;
    private JSONApi jsonApi;

    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
        this.jsonApi = new JSONApi();
    }

    public void listTemplates(RoutingContext routingContext) {
        templateRepository.getTemplates(lookup -> {
            if (lookup.failed()) {
                routingContext.fail(lookup.cause());
                return;
            }

            List<JsonObject> data = Lists.transform(lookup.result(), jsonApi::convertTemplate);

            routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, JSONApi.MIME_TYPE);
            routingContext.response().end(jsonApi.fromData(data).encode());
        });
    }

    public void addTemplate(RoutingContext routingContext) {
        Template template;
        try {
            template = jsonApi.parseJson(routingContext.getBodyAsJson());
        } catch (IllegalArgumentException e) {
            routingContext.response().setStatusCode(HttpResponseStatus.BAD_REQUEST.code()).end(e.getMessage());
            return;
        }

        templateRepository.addTemplate(template, result -> {
            if (result.failed()) {
                routingContext.fail(result.cause());
                return;
            }
            routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, JSONApi.MIME_TYPE);
            JsonObject data = jsonApi.convertTemplate(result.result());
            routingContext.response().end(jsonApi.fromData(data).encode());
        });
    }

    public void getTemplate(RoutingContext routingContext) {
        String templateID = routingContext.request().getParam("templateID");
        templateRepository.getTemplate(templateID, result -> {
            if (result.failed()) {
                routingContext.fail(result.cause());
                return;
            }

            Template template = result.result();
            if (template == null) {
                routingContext.response().setStatusCode(HttpResponseStatus.NOT_FOUND.code());
                routingContext.response().end(jsonApi.fromError(404, null).encode());
                return;
            }

            routingContext.response().putHeader(HttpHeaders.CONTENT_TYPE, JSONApi.MIME_TYPE);
            JsonObject data = jsonApi.convertTemplate(template);
            routingContext.response().end(jsonApi.fromData(data).encode());
        });
    }
}
