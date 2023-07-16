package com.rizky.secondhand.controller;

import com.rizky.secondhand.entity.ImageModel;
import com.rizky.secondhand.entity.Product;
import com.rizky.secondhand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addProduct(@RequestPart("product") Product product,
                              @RequestPart("imageFile")MultipartFile[] files) throws Exception {
        try {
            Set<ImageModel> images = uploadImage(files);
            product.setProductImages(images);
           return productService.addNewProduct(product);
        } catch (IOException e) {
            throw new Exception("Image not saved");
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] files) throws IOException {
        Set<ImageModel> imageModel = new HashSet<>();
        for (MultipartFile file : files) {
            ImageModel imageModel1 = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModel.add(imageModel1);
        }
        return imageModel;
    }


    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteProductById/{id}")
    public void deleteProductById(@PathVariable("id") Integer id) {
        productService.deleteProductById(id);
    }


    @GetMapping("/getProductDetailsById/{id}")
    public Product getProductById(@PathVariable("id") Integer id) {
        return productService.getProductById(id);
    }


    @GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId") Integer productId){

        return productService.getProductDetails(isSingleProductCheckout, productId);


    }

}
