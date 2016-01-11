package com.util.email;

import javax.mail.*;

public class MyAuthenticator extends Authenticator{
    String userName=null;
    String password=null;
      
    
    public MyAuthenticator(String username, String password) { 
        this.userName = username; 
        this.password = password;

    } 
    /**
     * 获得用户名和密码的验证结果
     */
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}