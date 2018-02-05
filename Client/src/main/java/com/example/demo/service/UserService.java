package com.example.demo.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface UserService {

    int addUser(Map user);

    List<Map> findAllUser(int pageNum, int pageSize);
}
