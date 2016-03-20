package com.github.dieterdepaepe.seshat.repo;

import com.github.dieterdepaepe.seshat.model.Template;

import java.util.List;

/**
 * Storage agnostic interface for retrieving/storing {@link Template}s.
 *
 * @author Dieter De Paepe
 */
public interface TemplateRepository {
    public List<Template> getTemplates();
    public Template addTemplate(Template template);
    public Template getTemplate(long id);
}
