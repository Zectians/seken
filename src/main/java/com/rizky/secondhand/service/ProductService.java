package com.rizky.secondhand.service;

import com.rizky.secondhand.dao.ProductDao;
import com.rizky.secondhand.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productDao.findAll();

    }

    public void deleteProductById(Integer id) {
        productDao.deleteById(id);
    }

    public Product getProductById(Integer id) {

        return productDao.findById(id).get();
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Integer productId) {
        if (isSingleProductCheckout){
//            buy single product
            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        }else {
//            buy entireCart
        }
    return new ArrayList<>();
    }
}
