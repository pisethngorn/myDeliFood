package com.DeliFood.dw.resources;

import com.DeliFood.dw.persistence.PersonDao;
import com.DeliFood.dw.representations.Person;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    private PersonDao personDao;

    public PersonResource(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Person getPerson(@PathParam("id") Integer id) {
        Person p = personDao.findById(id);
        if(p != null) {
            return p;
        }
        else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Timed
    public List<Person> listPersons() { return personDao.getAll(); }

    @POST
    @Timed
    public void save(Person person) {
        if(person != null && person.isValid()) {
            if(person.getId() != null) {
                personDao.update(person);
            }
            else {
                personDao.insert(person);
            }
        }
    }

    @DELETE
    @Path("/{id}")
    @Timed
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    public void deletePerson(@PathParam("id") Integer id) {
        if(personDao.findById(id) != null) {
            personDao.deleteById(id);
        }
        else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
