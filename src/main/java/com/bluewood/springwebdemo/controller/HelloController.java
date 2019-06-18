package com.bluewood.springwebdemo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //局部解决跨域问题
    @CrossOrigin(origins = "http://127.0.0.1:8080",maxAge = 3600)
    @GetMapping(value = "/ajax",produces = "application/json;charset=UTF-8")
    public String ajaxTest(Object object) {

        String jsonData="[{\"name\":\"zs\",\"age\":\"12\",\"address\":\"BeiJing\"},\n" +
                " {\"name\":\"lis\",\"age\":\"18\",\"address\":\"BeiJing\"},\n" +
                " {\"name\":\"wangwu\",\"age\":\"21\",\"address\":\"NewYork\"}\n" +
                "]";
        return jsonData;
    }
}
