package com.rizky.secondhand.dao;


import com.rizky.secondhand.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
}
