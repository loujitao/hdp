package com.steve.dataSet;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple1;

import java.util.Arrays;

public class Chap01_create {

    public static void main(String[] args) throws Exception{

        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

        test01(environment);
//        test02(environment);


    }

    private static void test01(ExecutionEnvironment environment) throws Exception{
        //1、从文件中创建
        DataSet<String> dataSource = environment.readTextFile("D:\\ideaWork\\hdp\\javaFlink\\src\\main\\resources\\wordcount.txt");
        dataSource.print();

//        DataSource<Tuple1<Person>> personDataSource = environment.readCsvFile("D:\\ideaWork\\hdp\\javaFlink\\src\\main\\resources\\person.csv")
//                .types(Person.class);
//        personDataSource.print();
    }

    private static void test02(ExecutionEnvironment environment) throws Exception{
        //2、从集合中创建
        DataSource<String> dataset02 = environment.fromCollection(Arrays.asList(new String[]{"A","B","C","D"}));
        dataset02.print();
    }

    //csv文件pojo实体类
class Person {
        public String name;
        public int age;
        public String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    }

}
