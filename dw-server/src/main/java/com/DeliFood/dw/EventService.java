package com.DeliFood.dw;

import com.DeliFood.dw.health.TemplateHealthCheck;
import com.DeliFood.dw.persistence.PersonDao;
import com.DeliFood.dw.representations.Person;
import com.DeliFood.dw.resources.HelloWorldResource;
import com.DeliFood.dw.resources.PersonResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventService extends Service<EventConfiguration> {

    private static List<Person> persons;

    static {
        persons = Collections.synchronizedList(new ArrayList<Person>());
        persons.add(new Person("Per", "per@kodemaker.no", "12345678"));
        persons.add(new Person("Magnus", "magnus@kodemaker.no"));
        persons.add(new Person("Ronny", "ronny@kodemaker.no"));
        persons.add(new Person("August", "august@kodemaker.no"));
        persons.add(new Person("Helge", "helge.jensen@finn.no"));
    }

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

        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create("jdbc:h2:mem:test", "username", "password");
        DBI jdbi = new DBI(jdbcConnectionPool);
        PersonDao personDao = jdbi.onDemand(PersonDao.class);
        personDao.createPersonTable();
        seedTheDatabase(personDao);

        environment.addResource(new PersonResource(personDao));
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

    private void seedTheDatabase(PersonDao personDao) {
        for(Person p : persons) {
            personDao.insert(p);
        }
    }
}
