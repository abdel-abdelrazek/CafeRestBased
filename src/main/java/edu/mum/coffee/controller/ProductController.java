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

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.genral.CustomErrorType;
import edu.mum.coffee.service.ProductService;
 
 
@RestController
@RequestMapping("/api")
public class ProductController {
 
    
    @Autowired
    ProductService productService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Users---------------------------------------------
 
    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> listAllUsers() {
        List<Product> products = productService.getAllProduct();
        if (products.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single User------------------------------------------
 
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        Product product = productService.getById(id);
        if (product == null) {
            
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
       
      
        productService.save(product);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a User ------------------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody Product pProduct) {
        
        Product product = productService.getById(id);
 
        if (product == null) {
            
            return new ResponseEntity(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        product.setDescription(pProduct.getDescription());
        product.setPrice(pProduct.getPrice());
        product.setProductName(pProduct.getProductName());
        product.setProductType(pProduct.getProductType());
        
        productService.save(product);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
 
    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
       
    	Product product = productService.getById(id);
        if (product == null) {
            
            return new ResponseEntity(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        productService.delete(product);;
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
 
  
 
}
