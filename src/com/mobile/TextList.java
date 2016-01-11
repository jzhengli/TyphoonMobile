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
		menu.add(0, 1, 0, "更新资料");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("你确定要更新吗?").setCancelable(false).setPositiveButton(
					"确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
								// 更新数据库
								//updateMaterials();
							InitTask init = new InitTask(lvAdapter);
							init.execute("");	
						}
					}).setNegativeButton("取消",
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
		// 可变长的输入参数，与AsyncTask.exucute()对应
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
			 * 改方法在执行实际的后台操作时被UI线程调用，可以在该方法中做一些准备工作
			 */
			super.onPreExecute();
			pdialog = new ProgressDialog(TextList.this);
			pdialog.setMessage("正在下载数据...");
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
				System.out.println("返回的结果："+result);
				// 实例化DocumentBuilderFactory
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				// 实例化DocumentBuilder
				DocumentBuilder builder = factory.newDocumentBuilder();
				// 获得Document
				Document doc = builder.parse(new  ByteArrayInputStream(result.getBytes()));
				// 获得节点列表
				NodeList nl = doc.getElementsByTagName("Materials");
				System.out.println("---------------------长度"+nl.getLength());
			
				publishProgress("正在更新数据库....."); 
				// 更新数据库
				database.execSQL("DROP TABLE IF EXISTS materials");
				System.out.println("---------------删除旧数据库");
				database.execSQL("CREATE TABLE materials (ID INTEGER PRIMARY KEY,title VARCHAR(30) NOT NULL,content TEXT NOT NULL);");
				
				for (int i = 0; i < nl.getLength(); i++) {
					// 解析XML文件获得id
					int id = Integer.parseInt(doc.getElementsByTagName("ID")
							.item(i).getFirstChild().getNodeValue());
					// 标题
					String title = doc.getElementsByTagName("title").item(i)
							.getFirstChild().getNodeValue();
					// 内容
					String content="";
					NodeList con=doc.getElementsByTagName("content").item(i).getChildNodes();
					for(int j=0;j<con.getLength();j++){
						content=content+con.item(j).getNodeValue();
					}
					System.out.println("------------------id="+id);
					System.out.println("------------------title="+title);
					System.out.println("------------------content="+content);
					// 插入到数据库
					database.execSQL("insert into materials values("+id+",'"+title+"','"+content+"')");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onProgressUpdate(String... values) {
			/* 
	         * 当publichProcess 呗调用以后，ＵＩ线程将调用这个有方法在界面上展示任务的情况，比如一个额进度条。这里是更新进度条 
	         */ 
			pdialog.setMessage(values[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			/* 
	         * 在doInbackground执行完成以后，onPostExecute将被调用，后台的结果将返回给UI线程，将获得图片显示出来 
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
