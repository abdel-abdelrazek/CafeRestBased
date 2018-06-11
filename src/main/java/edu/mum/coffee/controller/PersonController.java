package edu.mum.coffee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.genral.CustomErrorType;
import edu.mum.coffee.service.PersonService;

@RestController
@RequestMapping("/personapi")
public class PersonController {
 
    
    @Autowired
    PersonService personService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Users---------------------------------------------
 
    @GetMapping(value = "/Persons")
    public ResponseEntity<List<Person>> listAllUsers() {
        List<Person> Persons = personService.getAll();
        if (Persons.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Person>>(Persons, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single User------------------------------------------
 
    @GetMapping(value = "/Person/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        Person Person = personService.findById(id);
        if (Person == null) {
            
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(Person, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Person Person, UriComponentsBuilder ucBuilder) {
       
      
        personService.savePerson(Person);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(Person.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 

    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
       
    	Person Person = personService.findById(id);
        if (Person == null) {
            
            return new ResponseEntity(new CustomErrorType("Unable to delete. Person with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        personService.removePerson(Person);;
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }
 
  
 
}
