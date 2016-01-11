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
        this.setTitle("�û�����");
        
        initializeViews();
        passIntent();
        setListeners();
    }
    
    private EditText subject,content;
    private Button send;
    private String username,password;
    
    /**
     * ��ʼ���ؼ�
     */
    private void initializeViews(){
    	this.subject = (EditText)findViewById(R.id.myEditText2);
    	this.content = (EditText)findViewById(R.id.myEditText3);
    	this.send = (Button)findViewById(R.id.myButton1);
    }
    /**
     * ������ͼ����
     */
    private void passIntent(){
    	//Bundle bundle = this.getIntent().getExtras();
    	this.username = "289793584@qq.com";
    	this.password = "lZhEnG2009kwD";
    }
    /**
     * ���ð�ť����
     */
    private void setListeners(){
    	send.setOnClickListener(sendmail);
    }
    
    private Button.OnClickListener sendmail = new Button.OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//����Gmail�����ʼ�
			/**
			 * Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, receiver.getText().toString());
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject.getText().toString());
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,content.getText().toString());
			startActivity(emailIntent);
			*/
			
			//�����ʼ���Ϣ
			//������Ϣ
			MultiMailSenderInfo mailInfo = new MultiMailSenderInfo();
			mailInfo.setMailServerHost("smtp.qq.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			//�ʼ���Ϣ
			mailInfo.setUserName(username);
			mailInfo.setPassword(password);
			mailInfo.setFromAddress(username);
			mailInfo.setToAddress("347363127@qq.com");
			mailInfo.setSubject(subject.getText().toString());
			mailInfo.setContent(content.getText().toString());
			
			//�����ʼ�
			MultiMailsender mms = new MultiMailsender();
			mms.sendTextMail(mailInfo);
			
			Toast.makeText(getApplicationContext(), "�ʼ����ͳɹ�",Toast.LENGTH_SHORT).show();
			
			//mms.sendMailtoMultiReceiver(mailInfo);
			//mms.sendMailtoMultiCC(mailInfo);
		}
    	
    };
    
    protected void onPause(){
    	super.onPause();
    	finish();
    }
}
