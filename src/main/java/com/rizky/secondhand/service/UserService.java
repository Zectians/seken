package com.rizky.secondhand.service;

import com.rizky.secondhand.dao.RoleDao;
import com.rizky.secondhand.dao.UserDao;
import com.rizky.secondhand.entity.Role;
import com.rizky.secondhand.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(User user) {

        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }

    public void initUser(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setDescription("Admin Role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setDescription("Default User Role");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setUserPassword(getEncodedPassword("admin"));
        adminUser.setUserFirstName("Admin");
        adminUser.setUserLastName("Admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
        userDao.save(adminUser);

        User userApp = new User();
        userApp.setUsername("user");
        userApp.setUserPassword(getEncodedPassword("user"));
        userApp.setUserFirstName("user");
        userApp.setUserLastName("user");
        Set<Role> userAppRoles = new HashSet<>();
        userAppRoles.add(userRole);
        userApp.setRoles(userAppRoles);
        userDao.save(userApp);

        User usernew = new User();
        usernew.setUsername("newauser");
        usernew.setUserPassword(getEncodedPassword("tada"));
        usernew.setUserFirstName("tida");
        usernew.setUserLastName("pip");
        Set<Role> usernewRole = new HashSet<>();
        usernewRole.add(userRole);
        usernew.setRoles(usernewRole);
        userDao.save(usernew);

    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
