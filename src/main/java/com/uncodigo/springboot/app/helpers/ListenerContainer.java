package com.uncodigo.springboot.app.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ListenerContainer {

    @Autowired
    Environment environment;

    private final Logger logger = LoggerFactory.getLogger(ListenerContainer.class);

    @EventListener(ApplicationReadyEvent.class)
    public void postStartupPrint() throws UnknownHostException {
        logger.info("Application deployed under: http://"
                + InetAddress.getLocalHost().getHostAddress()
                + ":"
                + environment.getProperty("local.server.port"));
    }
}
