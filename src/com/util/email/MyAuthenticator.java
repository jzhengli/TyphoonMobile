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
     * ����û������������֤���
     */
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}