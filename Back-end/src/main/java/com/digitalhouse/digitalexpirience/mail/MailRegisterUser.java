package com.digitalhouse.digitalexpirience.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class MailRegisterUser extends MailType {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailRegisterUser.class);

	private static final String TEMPLATE_NAME = "email-confirm-account";

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("Confirme su cuenta")
	private String asuntoTemplate;

	@Override
	public String getAsunto() {
		return this.asuntoTemplate;
	}

	@Override
	public String getHtml(Map<String, Object> model) {
		String html = executeTemplate(model, templateEngine, TEMPLATE_NAME, false);
		LOGGER.trace(html);
		return html;
	}
}
