package com.eugen.onlineshop.dao;

import com.eugen.onlineshop.entity.User;

public interface DaoUserInetrface {

    User getUserByName(String name);

    void addUser(String name, String password);
}
