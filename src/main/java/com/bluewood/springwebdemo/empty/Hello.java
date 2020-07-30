package com.bluewood.springwebdemo.empty;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Hello {

    private Long id;
    private String name;
    private String sex;
    private Integer age;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

}
