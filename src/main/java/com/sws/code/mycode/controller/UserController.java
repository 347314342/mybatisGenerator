package com.sws.code.mycode.controller;

import com.sws.code.mycode.entity.User;
import com.sws.code.mycode.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/insert")
    public int insert(User entity) {
        return userService.insert(entity);
    }

    @RequestMapping("/deleteByPrimaryKey")
    public int deleteByPrimaryKey(Integer id) {
        return userService.deleteByPrimaryKey(id);
    }

    @RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(User record) {
        return userService.updateByPrimaryKey(record);
    }

    @RequestMapping("/selectAll")
    public List<User> selectAll() {
        return userService.selectAll();
    }
}