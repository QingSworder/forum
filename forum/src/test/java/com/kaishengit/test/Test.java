package com.kaishengit.test;

import com.kaishengit.entity.User;

/**
 * Created by wtj on 2016/12/15.
 */
public class Test {
    @org.junit.Test
    public void testSave(){
        User user = new User();
        user.setUsername("jack");
        user.setPassword("123456");
        user.setPhone("13123456789");


    }

}
