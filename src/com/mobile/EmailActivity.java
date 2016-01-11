package com.mobile;

import android.app.Activity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.util.email.*;
import com.util.email.MultiMailsender.MultiMailSenderInfo;

public class EmailActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);
        this.setTitle("用户反馈");
        
        initializeViews();
        passIntent();
        setListeners();
    }
    
    private EditText subject,content;
    private Button send;
    private String username,password;
    
    /**
     * 初始化控件
     */
    private void initializeViews(){
    	this.subject = (EditText)findViewById(R.id.myEditText2);
    	this.content = (EditText)findViewById(R.id.myEditText3);
    	this.send = (Button)findViewById(R.id.myButton1);
    }
    /**
     * 传递意图参数
     */
    private void passIntent(){
    	//Bundle bundle = this.getIntent().getExtras();
    	this.username = "289793584@qq.com";
    	this.password = "lZhEnG2009kwD";
    }
    /**
     * 设置按钮监听
     */
    private void setListeners(){
    	send.setOnClickListener(sendmail);
    }
    
    private Button.OnClickListener sendmail = new Button.OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//调用Gmail发送邮件
			/**
			 * Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, receiver.getText().toString());
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject.getText().toString());
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,content.getText().toString());
			startActivity(emailIntent);
			*/
			
			//设置邮件信息
			//连接信息
			MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
			mailInfo.setMailServerHost("smtp.qq.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			//邮件信息
			mailInfo.setUserName(username);
			mailInfo.setPassword(password);
			mailInfo.setFromAddress(username);
			mailInfo.setToAddress("347363127@qq.com");
			mailInfo.setSubject(subject.getText().toString());
			mailInfo.setContent(content.getText().toString());
			
			//发送邮件
			MultiMailsender mms = new MultiMailsender();
			mms.sendTextMail(mailInfo);
			
			Toast.makeText(getApplicationContext(), "邮件发送成功",Toast.LENGTH_SHORT).show();
			
			//mms.sendMailtoMultiReceiver(mailInfo);
			//mms.sendMailtoMultiCC(mailInfo);
		}
    	
    };
    
    protected void onPause(){
    	super.onPause();
    	finish();
    }
}
