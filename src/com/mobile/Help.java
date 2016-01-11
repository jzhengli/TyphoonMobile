package com.mobile;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Help extends Activity {
	EditText helpcontent, telephone;
	Button button;
	String help, phone, result,lat,lng;
	private ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		this.setTitle(R.string.item8);

		helpcontent = (EditText) findViewById(R.id.edittext1);
		telephone = (EditText) findViewById(R.id.edittext2);
		button = (Button) findViewById(R.id.helpbutton);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				help = helpcontent.getText().toString();
				phone = telephone.getText().toString();
				if (Splash.info != null && Splash.info.isAvailable()) {
					progressDialog = new ProgressDialog(Help.this);
					progressDialog.setMessage("�����ύ,���Ե�...");
					progressDialog.show();
					myHandler.post(sendThread);
				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							"�޷��������磡", Toast.LENGTH_LONG);
					toast.show();
				}
			}
		});
	}

	Handler myHandler = new Handler();
	Runnable sendThread = new Runnable() {
		public void run() {
			try {
				lat = Double.toString(Splash.location.getLatitude());
			    lng = Double.toString(Splash.location.getLongitude());
			    System.out.println("lat="+lat);
			    System.out.println("lng="+lng);
				String url = "http://"+Splash.ServerIp+"/Service1.asmx/helpSubmit";
				result = "";
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;
				Date date=new Date();
				String time=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
				System.out.println(time);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("helpcontent", help));
				params.add(new BasicNameValuePair("phone", phone));
				params.add(new BasicNameValuePair("submittime", time));
				params.add(new BasicNameValuePair("lat", lat));
				params.add(new BasicNameValuePair("lng", lng));

				HttpPost request = new HttpPost(url);
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient httpClient = new DefaultHttpClient();
				httpResponse = httpClient.execute(request);
				System.out.println("Code="+httpResponse.getStatusLine().getStatusCode());

				try {
					Socket sc = new Socket();
					InetSocketAddress isa=new InetSocketAddress(Splash.ServerIp, 5000);
					sc.connect(isa,5000);
					String msg = "�����Ϣ";
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(sc.getOutputStream())), true);
					out.println(msg);
					out.close();
					sc.close();
					System.out.println("Socket���ӳɹ���");
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					httpEntity = httpResponse.getEntity();
					result = EntityUtils.toString(httpEntity);
					System.out.println(result);
					result = ParseResult(result);
					System.out.println(result);
					if (result.equals("OK")) {
						progressDialog.dismiss();
						Toast toast = Toast.makeText(getApplicationContext(),
								"�ύ�ɹ���", Toast.LENGTH_LONG);
						toast.show();
					} else {
						progressDialog.dismiss();
						System.out.println(result);
						Toast toast = Toast.makeText(getApplicationContext(),
								"�ύʧ��!", Toast.LENGTH_LONG);
						toast.show();
					}
				} else {
					progressDialog.dismiss();
					Toast toast = Toast.makeText(getApplicationContext(),
							"��������ʧ�ܣ�", Toast.LENGTH_LONG);
					toast.show();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// �Դ������ȡ�����ݽ��д���ȥ���ļ�ͷ���ļ�β����������
	// �ļ�ͷ��ʽ�� <?xml version="1.0" encoding="utf-8" ?> <string
	// xmlns="http://qiuteng.org/">
	// �ļ�β��ʽ������������ص����������й� ���� boolean String int long �ȵ�
	public String ParseResult(String data) {
		int first = data.indexOf(">", 1);
		int second = data.indexOf(">", first + 1);

		int one = data.indexOf("<", 1);
		int two = data.indexOf("<", one + 1);

		String Data = data.substring(second + 1, two);
		return Data;

	}
}
