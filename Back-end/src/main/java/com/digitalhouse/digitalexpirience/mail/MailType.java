package com.digitalhouse.digitalexpirience.mail;

import org.thymeleaf.spring5.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.util.Map;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

public abstract class MailType {

    public abstract String getAsunto();

    public abstract String getHtml(Map<String, Object> model);

    public String executeTemplate(Map<String, Object> model,
                                  SpringTemplateEngine templateEngine,
                                  String templateName,
                                  boolean compress) {
        Context context = new Context();
        context.setVariables(model);
        String html = templateEngine.process(templateName, context);
        if (compress) {
            HtmlCompressor c = new HtmlCompressor();
            html = c.compress(html);
        }
        return html;
    }

}