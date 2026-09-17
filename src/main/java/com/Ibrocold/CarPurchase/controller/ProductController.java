package com.Ibrocold.CarPurchase.controller;

import com.Ibrocold.CarPurchase.model.Product;
import org.springframework.web.multipart.MultipartFile;
import com.Ibrocold.CarPurchase.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productservice;

    @GetMapping("/")
    public String greet(){
        return "Welcome To Cars Hub";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productservice.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/products/{Id}")
    public ResponseEntity<Product> getProductById(@PathVariable int Id){
        Product product = productservice.getProductById(Id);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {
            Product p = productservice.addProduct(product, imageFile);
            return new ResponseEntity<>(p,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/products/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productId){
        Product product = productservice.getProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok().body(imageFile);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProductById(@PathVariable int productId, @RequestPart Product product,
                                                    @RequestPart MultipartFile imageFile) throws IOException {
        Product p = productservice.updateProductById(productId, product, imageFile);
        if(p!=null){
            return new ResponseEntity<>("Update Successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/products/{Id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int productId){
        Product product = productservice.getProductById(productId);
        if(product!=null){
            productservice.deleteProductById(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductByKey(@RequestParam String value){
        List<Product> products = productservice.searchProducts(value);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
