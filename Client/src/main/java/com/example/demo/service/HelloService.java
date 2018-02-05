package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2018-01-26.
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;

    public User hiService(Integer id) {
        return  userRepository.findOne(id);
    }
}
