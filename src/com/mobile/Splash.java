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

	private final int SPLASH_DISPLAY_LENGHT = 1500; // �ӳ�����
	private final int BUFFER_SIZE = 400000;
	LocationManager locationManager;
	public static NetworkInfo info;
	public static String database = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ "com.mobile" + "/" + "mobile.db";
	public static String ServerIp="192.168.50.71";
	public static Location location; // �û�λ��
	public static String useraddress; // �û����ڵ�ַ
	public static String userprovice; // �û�����ʡ��
	public static String usercity; // �û����ڳ���

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

	// ��λ
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
					System.out.println("��ַ��" + useraddress + "  ʡ�ݣ�"
							+ userprovice + "  ���У�" + usercity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				Toast toast = Toast.makeText(getApplicationContext(),
						"��λʧ�ܣ�����GPS���ã�", Toast.LENGTH_LONG);
				toast.show();

				System.out.println("��λʧ�ܣ�����GPS���ã�");
			}
		} else {

			Toast toast = Toast.makeText(getApplicationContext(),
					"����������������磡", Toast.LENGTH_LONG);
			toast.show();

			System.out.println("����������������磡");
		}
	}

	// ��ʼ�����ݿ�
	void copyDataBase() {
		try {
			// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			if (!(new File(database).exists())) {
				InputStream is = this.getResources().openRawResource(
						R.raw.mobile); // ����������ݿ�
				FileOutputStream fos = new FileOutputStream(database);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				System.out.println("���ݿ�׼�����........");
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException�쳣��" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException�쳣��" + e.getMessage());
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
			httpConnection.disconnect(); // �ر�http����

			xmlcontent = sTotalString.toString();
			System.out.println("XML���ݣ�" + sTotalString.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return xmlcontent;
	}
}
