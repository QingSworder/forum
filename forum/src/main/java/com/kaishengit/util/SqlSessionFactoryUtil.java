package com.kaishengit.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by wtj on 2017/1/6.
 */
public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();

    private static SqlSessionFactory buildSqlSessionFactory() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException("读取mybatis.xml文件异常",e);
        }
    }
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
    //创建一个自动提交的Sqlsession
    public static SqlSession getSqlSession(Boolean isAutoCommit) {
        return sqlSessionFactory.openSession(isAutoCommit);
    }
}
