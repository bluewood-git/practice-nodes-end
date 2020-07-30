package com.bluewood.springwebdemo.eight;

/**
 * 函数式接口
 *
 * @author liming
 * @date 2020/7/28 14:10
 */
@FunctionalInterface
public interface Converter<T,F> {

    T converter(F f);

    default String putTo(F f){
        return f.toString();
    }
}
