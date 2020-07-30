package com.bluewood.springwebdemo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类，全局转换器
 *
 * @author liming
 * @date 2020/7/30 11:33
 */
@Configuration
public class WebMvnconfig implements WebMvcConfigurer  {

    //配置日期全局转换

    /**
     * 重写url路径匹配(忽略大小写敏感)
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        System.out.println(" ========== 路径匹配");

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        antPathMatcher.setCaseSensitive(false);
    }


    /**
     * JSON全局日期转换器
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        System.out.println(" ========== json全局日期转换");

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(CustomDateFormat.instance);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        System.out.println(" ========== 重写方法");

        converters.add(getMappingJackson2HttpMessageConverter());
    }

//    /**
//     * 表单全局日期转换器
//     */
//    @Bean
//    @Autowired
//    public ConversionService getConversionService(DateConverter dateConverter){
//        System.out.println(" ========== 表单全局日期转换");
//        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
//        Set<Converter> converters = new HashSet<>();
//        converters.add(dateConverter);
//        factoryBean.setConverters(converters);
//        return factoryBean.getObject();
//    }

}
