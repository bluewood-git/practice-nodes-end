package com.bluewood.eight;

import com.bluewood.springwebdemo.eight.Converter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * java 8测试类
 *
 * @author liming
 * @date 2020/7/28 18:32
 */
@Ignore
public class EightTests {

    @Test
    public void streamMethod(){

        String ss = new String();
        Converter<String,String> cn = String::toLowerCase;

        //Stream 测试
        Predicate<Integer> predicate = x -> x >100;
        List<Integer> list = new ArrayList<>();
        list.add(120);
        list.add(283);
        list.add(23);
        list.add(25);
        list.add(80);

        //Stream filter
        Stream<Integer> integerStream = list.stream().filter(x -> {
            System.out.println("GO!");
            return x > 100;
        });

        // 集合过滤，  predicate--过滤规则
        System.out.println("集合过滤： "+list.stream().filter(predicate).count());

        //stream map
        List<Integer> integerList = list.stream().map(num -> predicate.test(num)?num:num+1).collect(Collectors.toList());

        integerList.forEach(num -> System.out.println("num:" + num));

        List<Integer> list2 = new ArrayList<>();
        list2.add(300);
        list2.add(250);
        list2.add(18);


        //集合合并
        List<Integer> collect = Stream.of(list, list2).flatMap(num -> num.stream()).collect(Collectors.toList());
        System.out.println(collect);


        int reduce = Stream.of(1, 2, 3, 4).reduce(0, (sum, element) -> sum + element);
//        int reduce = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println("reduce = " + reduce);


    }

}
