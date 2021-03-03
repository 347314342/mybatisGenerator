package com.sws.code.mycode.service;

import com.sws.code.mycode.dao.UserDao;
import com.sws.code.mycode.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    protected UserDao userDao;

    public int insert(User entity) {
        return userDao.insert(entity);
    }

    public int deleteByPrimaryKey(Integer id) {
        return userDao.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }

    public List<User> selectAll() {
        return userDao.selectAll();
    }
}