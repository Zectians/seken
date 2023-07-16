package com.rizky.secondhand.service;

import com.rizky.secondhand.dao.RoleDao;
import com.rizky.secondhand.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role) {
        return roleDao.save(role);
    }
}
