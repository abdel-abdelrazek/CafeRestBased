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

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Order;
import edu.mum.coffee.genral.CustomErrorType;
import edu.mum.coffee.service.OrderService;
 
 
@RestController
@RequestMapping("/api")
public class OrderController {
 
    
    @Autowired
    OrderService orderService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Users---------------------------------------------
 
    @GetMapping(value = "/Orders")
    public ResponseEntity<List<Order>> listAllUsers() {
        List<Order> Orders = orderService.findAll();
        if (Orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(Orders, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single User------------------------------------------
 
    @GetMapping(value = "/Order/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        Order Order = orderService.findById(id);
        if (Order == null) {
            
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(Order, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Order Order, UriComponentsBuilder ucBuilder) {
       
      
        orderService.save(Order);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(Order.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 

 
    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
       
    	Order Order = orderService.findById(id);
        if (Order == null) {
            
            return new ResponseEntity(new CustomErrorType("Unable to delete. Order with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        orderService.delete(Order);;
        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
 
  
 
}
