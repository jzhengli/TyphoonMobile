package com.mobile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mobile.forecast.Weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
public class MainActivity extends Activity implements
		AdapterView.OnItemClickListener {

	private GridView gv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		gv = (GridView) this.findViewById(R.id.gridview_01);

		List<Map<String, Object>> data = this.getData();

		// 下面开始适配

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.menuitem, new String[] { "itemImage", "itemText" },
				new int[] { R.id.item_image, R.id.item_text });

		// 将其放在GridView中
		gv.setAdapter(adapter);

		// 监听单击事件
		gv.setOnItemClickListener(this); // 给类已经实现AdapterView.OnItemClickListener接口，在该类中实现onItemClick方法

	}

	public List<Map<String, Object>> getData() {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < 10; i++) { // 构造数据模型
			Map<String, Object> item = new HashMap<String, Object>();
			switch (i) {
			case 1:
				item.put("itemImage", R.drawable.item1);
				item.put("itemText", getResources().getString(R.string.item1));
				break;

			case 2:
				item.put("itemImage", R.drawable.item2);
				item.put("itemText", getResources().getString(R.string.item2));
				break;

			case 3:
				item.put("itemImage", R.drawable.item3);
				item.put("itemText", getResources().getString(R.string.item3));
				break;

			case 4:
				item.put("itemImage", R.drawable.item4);
				item.put("itemText", getResources().getString(R.string.item4));
				break;

			case 5:
				item.put("itemImage", R.drawable.item5);
				item.put("itemText", getResources().getString(R.string.item5));
				break;

			case 6:
				item.put("itemImage", R.drawable.item6);
				item.put("itemText", getResources().getString(R.string.item6));
				break;

			case 7:
				item.put("itemImage", R.drawable.item7);
				item.put("itemText", getResources().getString(R.string.item7));
				break;

			case 8:
				item.put("itemImage", R.drawable.item8);
				item.put("itemText", getResources().getString(R.string.item8));
				break;

			case 9:
				item.put("itemImage", R.drawable.item9);
				item.put("itemText", getResources().getString(R.string.item9));
				break;

			default:
				break;
			}

			data.add(item);
		}

		return data;
	}

	@SuppressWarnings("rawtypes")
	public void onItemClick(AdapterView parent, View v, int location, long id) {
		switch (location) {
		case 0:
			Intent intent0 = new Intent(MainActivity.this, TyphoonPath.class);
			startActivity(intent0);
			break;
		case 1:
			Intent intent1 = new Intent(MainActivity.this, BMyLocation.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 = new Intent(MainActivity.this, Weather.class);
			startActivity(intent2);
			break;
		case 3:
			Bundle b = new Bundle();
			b.putString("content", "SatelliteImage");
			Intent intent3 = new Intent(MainActivity.this, SatelliteImage.class);
			intent3.putExtras(b);
			startActivity(intent3);
			break;
		case 4:
			Intent intent4 = new Intent(MainActivity.this, RadarList.class);
			startActivity(intent4);
			break;
		case 5:
			Intent intent5 = new Intent(MainActivity.this, TextList.class);
			startActivity(intent5);
			break;
		case 6:
			Intent intent6 = new Intent(MainActivity.this, ImageUpload.class);
			startActivity(intent6);
			break;
		case 7:
			Intent intent7 = new Intent(MainActivity.this, Help.class);
			startActivity(intent7);
			break;
		case 8:
			Intent intent8 = new Intent(MainActivity.this, EmailActivity.class);
			startActivity(intent8);
			break;
		}
		// 在这里处理每一个item的单击事件
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("确认退出吗？");
			builder.setPositiveButton("确认", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					MainActivity.this.finish();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}