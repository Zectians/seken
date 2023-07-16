package com.rizky.secondhand.dao;

import com.rizky.secondhand.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, String> {
}
