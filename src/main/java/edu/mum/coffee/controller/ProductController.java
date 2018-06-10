package edu.mum.coffee.controller;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.myResponse.Response;
import edu.mum.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    Response response;

    @PostMapping("/create")
    public Response createProduct(@RequestBody Product product) {
        Product productRes = productService.save(product);
        if (productRes != null) {
            response.setErrorCode(200);
            response.setMessage("Adding Successed.");
        } else {
            response.setErrorCode(500);
            response.setMessage("Adding Failed.");
        }
        return response;
    }

    @GetMapping("/all")
    public List<Product> listAllProducts() {
        return productService.getAllProduct();
    }

    @PutMapping("/update")
    public Response updateProduct(@RequestBody Product product) {
        Product res = productService.save(product);
        if (res != null) {
            response.setErrorCode(200);
            response.setMessage("Updating Successed.");
        } else {
            response.setErrorCode(500);
            response.setMessage("Updating Failed");
        }
        return response;
    }

    @DeleteMapping("/delete")
    public Response deleteProduct(@RequestBody Product product) {
        try {
            productService.delete(product);
            response.setErrorCode(200);
            response.setMessage("Deleting Success.");
        } catch (Exception ex) {
            response.setErrorCode(500);
            response.setMessage("Deleting Failed.");
        }
        return response;
    }

    @GetMapping("/byId/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getById(id);
    }
}
