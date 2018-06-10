package edu.mum.coffee.controller;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.myResponse.Response;
import edu.mum.coffee.myResponse.Response;
import edu.mum.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    Response customResponse;

    @PostMapping("create")
    public Response createPerson(@RequestBody Person person) {
        Person personRes = personService.savePerson(person);
        if (personRes != null) {
        	customResponse.setMessage("Adding Successed.");
            customResponse.setErrorCode(200);
            
        } else {
        	customResponse.setMessage("Adding Failed.");
            customResponse.setErrorCode(500);
            
        }
        return customResponse;
    }

    @PutMapping("/update")
    public Response updatePerson(@RequestBody Person person) {
        Person personRes = personService.savePerson(person);
        if (personRes != null) {
        	customResponse.setMessage("Updating Successed");
            customResponse.setErrorCode(200);
            
        } else {

            customResponse.setMessage("Updating Failed");
            customResponse.setErrorCode(500);
        }
        return customResponse;
    }

    @GetMapping("/all")
    public List<Person> listAllPersons() {
        return personService.getAll();
    }

    @GetMapping("/byId/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }
}
