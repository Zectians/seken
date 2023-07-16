package com.rizky.secondhand.service;

import com.rizky.secondhand.configuration.JwtFilter;
import com.rizky.secondhand.dao.OrderDetailDao;
import com.rizky.secondhand.dao.ProductDao;
import com.rizky.secondhand.dao.UserDao;
import com.rizky.secondhand.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private static String ORDER_STATUS = "ORDERED";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public void placeOrder(OrderInput orderInput) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList){
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtFilter.CURRENT_USER;
           User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_STATUS,
                    product.getProductActualPrice() * o.getQuantity(),
                    product,
                    user
            );

            orderDetailDao.save(orderDetail);

        }
    }
}
