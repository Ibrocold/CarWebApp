package com.Ibrocold.CarPurchase.service;

import com.Ibrocold.CarPurchase.model.Product;
import com.Ibrocold.CarPurchase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@Service
public class ProductService {

    @Autowired
    private ProductRepository productrepository;

    public List<Product> getAllProducts(){
        return productrepository.findAll();
    }

    public Product getProductById(int Id){
       return productrepository.findById(Id).orElse(null);
    }

    public  Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return productrepository.save(product);
    }

    public Product updateProductById(int Id, Product product, MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return productrepository.save(product);
    }
    public void deleteProductById(int productId){
        productrepository.deleteById(productId);
    }

    public List<Product> searchProducts(String value){
        return productrepository.searchByKeyWord(value);
    }





}
