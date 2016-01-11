package com.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.mobile.handler.GeocodeXmlHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Splash extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1500; // 延迟三秒
	private final int BUFFER_SIZE = 400000;
	LocationManager locationManager;
	public static NetworkInfo info;
	public static String database = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ "com.mobile" + "/" + "mobile.db";
	public static String ServerIp="192.168.50.71";
	public static Location location; // 用户位置
	public static String useraddress; // 用户所在地址
	public static String userprovice; // 用户所在省份
	public static String usercity; // 用户所在城市

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		located();
		copyDataBase();

		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent mainIntent = new Intent(Splash.this, MainActivity.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

	// 定位
	void located() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = locationManager.getProviders(true);
			LocationListener ll = new LocationListener() {
				public void onLocationChanged(Location location) {

				}

				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub

				}

				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub

				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub

				}

			};

			for (String provider : providers) {
				locationManager.requestLocationUpdates(provider, 0, 1000, ll);
				location = locationManager.getLastKnownLocation(provider);
			}
			if (location != null) {
				double lng = location.getLongitude();
				double lat = location.getLatitude();
				System.out.println("Lon=" + location.getLongitude());
				System.out.println("Lat=" + location.getLatitude());
				try {
					SAXParserFactory factory = SAXParserFactory.newInstance();
					XMLReader reader = factory.newSAXParser().getXMLReader();
					GeocodeXmlHandler handler = new GeocodeXmlHandler();
					reader.setContentHandler(handler);
					reader.parse(new InputSource(new StringReader(geocodeAddr(
							lat, lng))));

					useraddress = handler.getaddress();
					userprovice = handler.getprovince();
					usercity = handler.getcity();
					System.out.println("地址：" + useraddress + "  省份："
							+ userprovice + "  城市：" + usercity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				Toast toast = Toast.makeText(getApplicationContext(),
						"定位失败，请检查GPS设置！", Toast.LENGTH_LONG);
				toast.show();

				System.out.println("定位失败，请检查GPS设置！");
			}
		} else {

			Toast toast = Toast.makeText(getApplicationContext(),
					"网络错误，请设置网络！", Toast.LENGTH_LONG);
			toast.show();

			System.out.println("网络错误，请设置网络！");
		}
	}

	// 初始化数据库
	void copyDataBase() {
		try {
			// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
			if (!(new File(database).exists())) {
				InputStream is = this.getResources().openRawResource(
						R.raw.mobile); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(database);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				System.out.println("数据库准备完成........");
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException异常：" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException异常：" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String geocodeAddr(double lat, double lng) {
		String xmlcontent = "";
		String urlString = "http://ditu.google.com/maps/geo?q=+" + lat + ","
				+ lng + "&output=xml&oe=utf8&hl=zh-CN&sensor=false";
		// String urlString =
		// "http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&language=zh_CN&sensor=false";
		StringBuilder sTotalString = new StringBuilder();
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			InputStream urlStream = httpConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlStream));
			String sCurrentLine = "";
			while ((sCurrentLine = bufferedReader.readLine()) != null) {
				sTotalString.append(sCurrentLine);
			}
			bufferedReader.close();
			httpConnection.disconnect(); // 关闭http连接

			xmlcontent = sTotalString.toString();
			System.out.println("XML内容：" + sTotalString.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return xmlcontent;
	}
}
