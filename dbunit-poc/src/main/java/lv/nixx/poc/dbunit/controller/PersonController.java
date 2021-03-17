package lv.nixx.poc.dbunit.controller;

import lv.nixx.poc.dbunit.dao.PersonDAO;
import lv.nixx.poc.dbunit.dao.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PersonController {

    private PersonDAO dao;

    @Autowired
    public PersonController(PersonDAO dao) {
        this.dao = dao;
    }

    @GetMapping("/person")
    public Collection<Person> getAllPersons() {
        return dao.getAllPersons();
    }
}
