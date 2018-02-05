package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.service.HelloService;
import com.example.demo.state.ClientApiFinal;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 注解全名：
 * @Description 使用RestController 相当于@Controller 和 @RequestBody
 * @author Administrator
 * @date 2017-4-21 下午9:06:28
 * @version V1.3.1
 */
// 相当于 @Controller + @ResponseBody
// 该注解 方法method 返回类型是String时候则返回string,返回对象时候则讲json_encode 该对象的json字符串
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HelloService helloService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 请求： http://localhost:8800/test/ http方式：get 请求返回contentType: text/plain
     * 请求responseBody: "Hello page"
     *
     * @Description
     * @author Administrator
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Hello page";
    }

    @GetMapping("/hi")
    public String hi(){
        return "I'm forezp";
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public User findById(@PathVariable Integer id){
        User findOne = this.helloService.hiService(id);
        if(!redisUtil.exists(ClientApiFinal.RedisUser))
            redisUtil.set(ClientApiFinal.RedisUser,findOne);
        return findOne;
    }


    @RequestMapping(value="lk/{id}",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public User findById1(@PathVariable Integer id){
        return this.restTemplate.getForObject("http://localhost:8762/"+id,User.class);
    }

    @Value("${server.port}")
    String port;

    @GetMapping(value = "/hiHome")
    public String homeUsae(@RequestParam String name) {
        return "自定义服务器：hi "+name+",i am from port:";
    }
}
