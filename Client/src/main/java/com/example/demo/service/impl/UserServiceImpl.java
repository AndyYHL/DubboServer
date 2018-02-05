package com.example.demo.service.impl;

import com.example.demo.dao.read.UserReadDao;
import com.example.demo.dao.write.UserWriteDao;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/16.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserReadDao userReadDao;//这里会报错，但是并不会影响
    @Autowired
    private UserWriteDao userWriteDao;//这里会报错，但是并不会影响

    @Override
    public int addUser(Map map) {

        return userWriteDao.save(map);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<Map> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userReadDao.findLimit(null);
    }
}
