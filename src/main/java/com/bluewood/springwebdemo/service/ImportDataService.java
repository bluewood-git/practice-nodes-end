package com.bluewood.springwebdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 数据导入
 *
 * @author liming
 * @date 2020/7/31 15:38
 */
public class ImportDataService {

    private static ExecutorService executor = new ThreadPoolExecutor(10, 10,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue(10));

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("i: "+i);
        }
        new ImportDataService().importData(list);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" === " );
        new ImportDataService().importData(list);
    }

    public void importData(List<String> data){
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //数据分组 分为10组
        List<List<String>> lists = averageAssign(data, 10);

        for (List<String> list : lists) {
            new Thread(() -> {
//                    list.save();
                System.out.println(Thread.currentThread().getName()+ "  list = " + list+" is saved");
            }).start();
        }
//        executorService.shutdown();
    }

    /**
     * 将一组数据平均分成n组
     *
     * @param source 要分组的数据源
     * @param n      平均分成n组
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
