package com.eugen.onlineshop.service;

import com.eugen.onlineshop.dao.DaoUserInetrface;
import com.eugen.onlineshop.entity.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class UserService {
    private List<String> sessionList;
    private DaoUserInetrface userDao;
    private static final String SALT = "sadjgbsadjhp12351246plsdf";

    public UserService(List<String> sessionList, DaoUserInetrface userDao) {
        this.sessionList = sessionList;
        this.userDao = userDao;
    }

    public boolean checkPassword(String name, String password) {
        User user = userDao.getUserByName(name);
        return encryptPassword(password).equals(user.getPassword());
    }

    public void addUser(String name, String password) {
        String encodedPassword = encryptPassword(password);
        userDao.addUser(name, encodedPassword);

    }

    public String generateToken() {
        String uuid = UUID.randomUUID().toString();
        sessionList.add(uuid);
        return uuid;
    }

    public boolean isTokenExist(String token) {

        if (sessionList.contains(token)) {
            return true;
        }
        return false;
    }

    private static String encryptPassword(String password) {

        password += SALT;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                stringBuilder.append(String.valueOf(hash[i]));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
