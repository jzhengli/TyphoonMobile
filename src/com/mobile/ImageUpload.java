package com.mobile;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageUpload extends Activity {

	Button selectButton, submitButton;
	EditText disasterContent, imagepath;
	ImageView imageView;
	String imagePath, result,submitcontent;
	private static int SELECT_PICTURE;
	private ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileupload);
		this.setTitle(R.string.item7);

		imageView = (ImageView) findViewById(R.id.imageview);
		disasterContent = (EditText) findViewById(R.id.disasteredittext);
		imagepath = (EditText) findViewById(R.id.imagePath);
		selectButton = (Button) findViewById(R.id.selectButton);
		submitButton = (Button) findViewById(R.id.submitButton);

		selectButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "ѡ��ͼƬ"),
						SELECT_PICTURE);
			}

		});

		submitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (Splash.info != null && Splash.info.isAvailable()) {
				progressDialog = new ProgressDialog(ImageUpload.this);
				progressDialog.setMessage("�����ϴ�,���Ե�...");
				progressDialog.show();
				myHandler.post(sendThread);
				}else{
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
				result = "";
				submitcontent=disasterContent.getText().toString();
				FileInputStream fis = new FileInputStream(imagePath);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = fis.read(buffer)) >= 0) {
					baos.write(buffer, 0, count);
				}

				String uploadBuffer = new String(Base64.encodeBytes(baos
						.toByteArray())); // ����Base64����
				System.out.println("Base64������ͼƬ����:"+uploadBuffer.length());
				System.out.println("Base64������ͼƬ��"+uploadBuffer);
				String url = "http://"+Splash.ServerIp+"/Service1.asmx/disasterUpload";
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;
				Date date=new Date();
				String time=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("submitcontent", submitcontent));
				params.add(new BasicNameValuePair("uimage", uploadBuffer));				
				params.add(new BasicNameValuePair("uploadtime", time));
				Log.i("connectWebService", "start");
				fis.close();

				HttpPost request = new HttpPost(url);
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient httpClient = new DefaultHttpClient();
				httpResponse = httpClient.execute(request);
				
				try {
					Socket sc = new Socket(Splash.ServerIp, 5000);
					String msg = "������Ϣ";
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(sc.getOutputStream())), true);
					out.println(msg);
					out.close();
					sc.close();
					System.out.println("Socket���ӳɹ���");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					httpEntity = httpResponse.getEntity();
					result = EntityUtils.toString(httpEntity);
					System.out.println(result);
					result = ParseResult(result);
					System.out.println(result);
					if (result.equals("1")) {
						progressDialog.dismiss();
						Toast toast = Toast.makeText(getApplicationContext(),
								"�ϴ��ɹ���", Toast.LENGTH_LONG);
						toast.show();
					} else {
						progressDialog.dismiss();
						Toast toast = Toast.makeText(getApplicationContext(),
								"�ϴ�ʧ�ܣ�", Toast.LENGTH_LONG);
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				try {
					ContentResolver resolver = getContentResolver();
					Uri originalUri = data.getData();
					Bitmap bm = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					BitmapDrawable drawable = new BitmapDrawable(bm);
					imageView.setImageDrawable(drawable);
					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor cursor = managedQuery(originalUri, proj, null, null,
							null);
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					imagePath = cursor.getString(column_index);
					imagepath.setText(imagePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
