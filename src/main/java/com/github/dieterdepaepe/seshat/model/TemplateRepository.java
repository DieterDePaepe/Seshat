package com.github.dieterdepaepe.seshat.model;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Storage agnostic interface for retrieving/storing {@link Template}s.
 *
 * @author Dieter De Paepe
 */
public interface TemplateRepository {
    /**
     * Retrieves all stored templates.
     * @param handler the handler
     */
    void getTemplates(Handler<AsyncResult<List<? extends Template>>> handler);

    /**
     * Persist a template.
     * @param newTemplate the template to store
     * @param handler the handler which will receive an updated version of the template
     */
    void addTemplate(Template newTemplate, Handler<AsyncResult<Template>> handler);

    /**
     * Retrieves the template with the specified id, or {@code null}.
     * @param id an id
     * @param handler the handler for the resulting template
     */
    void getTemplate(Object id, Handler<AsyncResult<Template>> handler);
}
