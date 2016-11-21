package com.DeliFood.dw;

import com.DeliFood.dw.health.TemplateHealthCheck;
import com.DeliFood.dw.resources.HelloWorldResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Created by Piseth on 11/20/16.
 */
public class EventService extends Service<EventConfiguration> {

    public static void main(String[] args) throws Exception {
        new EventService().run(args);
    }

    @Override
    public void initialize(Bootstrap<EventConfiguration> bootstrap) {
        bootstrap.setName("dw-server");
    }

    @Override
    public void run(EventConfiguration eventConfiguration, Environment environment) throws Exception {
        String template = eventConfiguration.getTemplate();
        String defaultName = eventConfiguration.getDefaultName();


        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }
}
