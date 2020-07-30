package com.bluewood.springwebdemo.eight;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author liming
 * @date 2020/7/28 14:11
 */

public class EightTest {

    public static void main(String[] args) {

        //使用匿名内部类方式
        // Interface Converter 为函数式接口
        Converter<String,Integer> converter = new Converter<String, Integer>() {
            @Override
            public String converter(Integer integer) {
                return integer.toString();
            }
        };
        String converString = converter.converter(123);
        System.out.println("converString = " + converString);

        // default 方法
        String s = converter.putTo(123);
        System.out.println("default 方法 = " + s);

        //使用lambda表达式
        Converter<Integer, String> converter1 = (from) -> Integer.valueOf(from);

        Integer conInteger = converter1.converter("1232");
        System.out.println("conInteger = " + conInteger);


        //使用静态引用 ::
        Converter<Integer, String> converter2 = Integer::valueOf;
        System.out.println("converter2 静态引用： "+converter2.converter("3333"));

        //引用对象的方法
        EightTest eightTest = new EightTest();
        Converter<String,String> converter3 = eightTest::startWith;
        System.out.println("startWith:  "+converter3.converter("JAVA"));

        Converter<String,String> cn = String::toLowerCase;

        System.out.println("To lowercase  "+cn.converter("ToLowerCase"));

        //java.util.function 包下
        Predicate<Integer> predicate = x -> x > 6;
        if(predicate.test(8)){
            System.out.println("8 is bigger to 6");
        }
        predicate.negate().test(8);//对test结果取反


        Predicate<Integer> bigerThan6 = x -> {
            System.out.println("you are welcome "+x);
            return x > 6;
        };

        boolean test = bigerThan6.test(12);
        System.out.println("test = " + test);
    }


    public void streamMethod(){

        String ss = new String();
        Converter<String,String> cn = String::toLowerCase;

        //Stream 测试
        List<Integer> list = new ArrayList<>();
        list.add(120);
        list.add(283);
        list.add(23);
        list.add(25);
        list.add(80);

        long count = list.stream().filter(x -> x >100).count();
        System.out.println("count = " + count);
    }

    String startWith(String s ){
        return String.valueOf(s.charAt(0));
    }


}
