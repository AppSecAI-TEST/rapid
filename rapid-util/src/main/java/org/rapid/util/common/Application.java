package org.rapid.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

public abstract class Application implements ApplicationListener<ApplicationContextEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Override
	public final void onApplicationEvent(ApplicationContextEvent event) {
		if (event instanceof ContextStartedEvent)
			start();
		else if (event instanceof ContextStoppedEvent)
			stop();
		else
			logger.info("Spring refresh and closed were ignored!");
	}
	
	protected abstract void start();
	
	protected abstract void stop();
}
