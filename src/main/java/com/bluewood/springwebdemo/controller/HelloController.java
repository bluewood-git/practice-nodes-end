package com.bluewood.springwebdemo.controller;

import com.bluewood.springwebdemo.empty.Hello;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    //局部解决跨域问题
    @CrossOrigin(origins = "http://127.0.0.1:8080",maxAge = 3600)
    @GetMapping(value = "/ajax",produces = "application/json;charset=UTF-8")
    public Map ajaxTest(Object object) {

//        String jsonData="[{\"name\":\"zs\",\"age\":\"12\",\"address\":\"BeiJing\"},\n" +
//                " {\"name\":\"lis\",\"age\":\"18\",\"address\":\"BeiJing\"},\n" +
//                " {\"name\":\"wangwu\",\"age\":\"21\",\"address\":\"NewYork\"}\n" +
//                "]";
        String jsonData="{\n" +
                "    \"jsonmap\": {\n" +
                "        \"手机号\": \"1825645885\"\n" +
                "    }\n" +
                "}";
        Map<String, Object> map = new HashMap<>();
        map.put("手机号", "25552488");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("jsonMap", map);

        return map2;
    }

    @PostMapping("/hello")
    public String hello(@RequestBody Hello hello){
        System.out.println(hello.getName());
        System.out.println("birthday "+hello.getBirthday());
        return hello.getBirthday().toString();
    }
}
