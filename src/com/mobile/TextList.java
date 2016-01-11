package com.mobile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TextList extends Activity{
	private ListView list;
    private ArrayList<String> lvList;
    private ArrayAdapter<String> lvAdapter;
    private SQLiteDatabase database;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.setTitle(R.string.item6);
        
        list=(ListView)findViewById(R.id.ListView1);
        lvList = new ArrayList<String>();
        
        database = SQLiteDatabase.openOrCreateDatabase(Splash.database, null);
		Cursor cur = database.rawQuery(
				"SELECT title from materials", null);
		while(cur.moveToNext()){
			String title=cur.getString(0);
			lvList.add(title);
		}
		cur.close();
		database.close();
		        
        lvAdapter = new ArrayAdapter<String>(this, R.layout.listviewitem,lvList);
        list.setAdapter(lvAdapter);
        list.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> arg0,
                    View arg1, int arg2, long arg3) {
            	Bundle b=new Bundle();
            	b.putString("title", lvList.get(arg2));
            	Intent intent = new Intent(TextList.this, MaterialText.class);
    			intent.putExtras(b);
    			startActivity(intent);
            }
        } );
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "��������");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("��ȷ��Ҫ������?").setCancelable(false).setPositiveButton(
					"ȷ��", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
								// �������ݿ�
								//updateMaterials();
							InitTask init = new InitTask(lvAdapter);
							init.execute("");	
						}
					}).setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
			break;
		}
		return true;
	}
	
	private class InitTask extends AsyncTask<String, String, String> {
		// �ɱ䳤�������������AsyncTask.exucute()��Ӧ
		ProgressDialog pdialog;
		ArrayAdapter<String> Adapter;

		public InitTask(ArrayAdapter<String> lvAdapter) {
			lvList.clear();
			database = SQLiteDatabase.openOrCreateDatabase(Splash.database, null);
			this.Adapter=lvAdapter;
		}

		@Override
		protected void onPreExecute() {
			/*
			 * �ķ�����ִ��ʵ�ʵĺ�̨����ʱ��UI�̵߳��ã������ڸ÷�������һЩ׼������
			 */
			super.onPreExecute();
			pdialog = new ProgressDialog(TextList.this);
			pdialog.setMessage("������������...");
			pdialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				String urlStr = "http://"+Splash.ServerIp+"/Service1.asmx/updateMaterials";
				String result="";
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;
				HttpPost request = new HttpPost(urlStr);
				HttpClient httpClient = new DefaultHttpClient();
				httpResponse = httpClient.execute(request);
				System.out.println("Code="+httpResponse.getStatusLine().getStatusCode());
				
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					httpEntity = httpResponse.getEntity();
					result = EntityUtils.toString(httpEntity);
					System.out.println(result);
				}
				System.out.println("���صĽ����"+result);
				// ʵ����DocumentBuilderFactory
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				// ʵ����DocumentBuilder
				DocumentBuilder builder = factory.newDocumentBuilder();
				// ���Document
				Document doc = builder.parse(new  ByteArrayInputStream(result.getBytes()));
				// ��ýڵ��б�
				NodeList nl = doc.getElementsByTagName("Materials");
				System.out.println("---------------------����"+nl.getLength());
			
				publishProgress("���ڸ������ݿ�....."); 
				// �������ݿ�
				database.execSQL("DROP TABLE IF EXISTS materials");
				System.out.println("---------------ɾ�������ݿ�");
				database.execSQL("CREATE TABLE materials (ID INTEGER PRIMARY KEY,title VARCHAR(30) NOT NULL,content TEXT NOT NULL);");
				
				for (int i = 0; i < nl.getLength(); i++) {
					// ����XML�ļ����id
					int id = Integer.parseInt(doc.getElementsByTagName("ID")
							.item(i).getFirstChild().getNodeValue());
					// ����
					String title = doc.getElementsByTagName("title").item(i)
							.getFirstChild().getNodeValue();
					// ����
					String content="";
					NodeList con=doc.getElementsByTagName("content").item(i).getChildNodes();
					for(int j=0;j<con.getLength();j++){
						content=content+con.item(j).getNodeValue();
					}
					System.out.println("------------------id="+id);
					System.out.println("------------------title="+title);
					System.out.println("------------------content="+content);
					// ���뵽���ݿ�
					database.execSQL("insert into materials values("+id+",'"+title+"','"+content+"')");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onProgressUpdate(String... values) {
			/* 
	         * ��publichProcess �µ����Ժ󣬣գ��߳̽���������з����ڽ�����չʾ��������������һ����������������Ǹ��½����� 
	         */ 
			pdialog.setMessage(values[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			/* 
	         * ��doInbackgroundִ������Ժ�onPostExecute�������ã���̨�Ľ�������ظ�UI�̣߳������ͼƬ��ʾ���� 
	         */ 
			Cursor cur = database.rawQuery(
					"SELECT title from materials", null);
			while(cur.moveToNext()){
				String title=cur.getString(0);
				lvList.add(title);
			}
			cur.close();
			database.close();
			
			Adapter.notifyDataSetChanged();
			pdialog.dismiss();
		}
	}
}
