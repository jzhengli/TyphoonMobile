package com.mobile.forecast;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mobile.R;
import com.mobile.Splash;
import com.mobile.handler.GoogleWeatherHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Weather extends ListActivity {
	private double lon; // ����
	private double lat; // ά��
	private String httpurl;
	private URL url;
	private WeatherSet mySet;
	private static final int GUI_STATR_SHOW = 0x100;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		this.setTitle(R.string.item3);
		this.getListView();

		if (Splash.location != null) {
			lon = Splash.location.getLongitude();
			lat = Splash.location.getLatitude();
			int a = (int) (lat * 1E6);
			int b = (int) (lon * 1E6);
			httpurl = "http://www.google.com/ig/api?hl=zh_cn&weather=,,," + a
					+ "," + b;

			new Thread(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub

					try {
						// ����һ������������
						SAXParserFactory factory = SAXParserFactory
								.newInstance();
						// �õ�XMLReader����
						XMLReader reader = factory.newSAXParser()
								.getXMLReader();
						// ΪXMLReader�������ݴ�����
						mySet = new WeatherSet();
						reader.setContentHandler(new GoogleWeatherHandler(mySet));
						// ��ʼ�����ļ�,StringReader�����ķ�ʽ�������ַ���
						url = new URL(httpurl);
						InputStreamReader isr = new InputStreamReader(url
								.openStream(), "GBK");
						reader.parse(new InputSource(isr));
						// ֪ͨUI��ʾ������Ϣ
						Message m = new Message();
						m.what = Weather.GUI_STATR_SHOW;
						Weather.this.showInfoHandler.sendMessage(m);
					} catch (Exception e) {
						Toast toast = Toast.makeText(getApplicationContext(),
								"�޷���ȡ���ݣ�", Toast.LENGTH_LONG);
						toast.show();
						e.printStackTrace();
					}
				}
			}).start();
		}else{
			Toast toast = Toast.makeText(getApplicationContext(),
					"��λʧ�ܣ��޷���ʾ������Ϣ��", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	Handler showInfoHandler = new Handler() {
		public void handleMessage(Message msg) {
			// ��ʾ��Ϣ
			switch (msg.what) {
			case Weather.GUI_STATR_SHOW:
				try {
					ImageView forecastImage = (ImageView) findViewById(R.id.dForecastImage);
					TextView cityText = (TextView) findViewById(R.id.dCityText);
					TextView conditionText = (TextView) findViewById(R.id.dConditionText);
					TextView humidityText = (TextView) findViewById(R.id.dHumidityText);
					TextView windText = (TextView) findViewById(R.id.dWindText);
					TextView tempcText = (TextView) findViewById(R.id.dTempCText);
					forecastImage.setImageResource(ForecastUtil
							.getForecastImage((mySet.cwc.getimage())));
					cityText.setText(mySet.cwc.getCity());
					conditionText.setText(mySet.cwc.getCondition());
					humidityText.setText(mySet.cwc.getHumidity());
					windText.setText(mySet.cwc.getWindCondition());
					tempcText.setText(mySet.cwc.getTempC() + "��");
					updateAnimtation();
					SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
							getData(), R.layout.detailitems, new String[] {
									"dDetailImage", "ddDayText",
									"ddConditionText", "ddTempCText" },
							new int[] { R.id.dDetailImage, R.id.ddDayText,
									R.id.ddConditionText, R.id.ddTempCText });
					setListAdapter(adapter);
				} catch (Exception e) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"�޷���ȡ���ݣ�", Toast.LENGTH_LONG);
					toast.show();
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i <= 3; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dDetailImage",
					ForecastUtil.getDetailForecastIcon(mySet
							.getForecastWeathes().get(i).getimage()));
			map.put("ddDayText", mySet.getForecastWeathes().get(i)
					.getforecastDate());
			map.put("ddConditionText", mySet.getForecastWeathes().get(i)
					.getforecastCondition());
			map.put("ddTempCText", mySet.getForecastWeathes().get(i).getTempC());
			list.add(map);
		}
		return list;
	}

	/**
	 * ����Ч��
	 * 
	 * @param iconDescription
	 */
	private void updateAnimtation() {

		/**
		 * ��ȡ����ǰ����״����ͼ���ID
		 */
		int icon = ForecastUtil.getCurrentForecastIcon((mySet.cwc.getimage()));
		/**
		 * ��ȡͼ����ʾ�ؼ�
		 */
		ImageView currentIcon = (ImageView) findViewById(R.id.dForecastImage);

		/**
		 * ����͸�����ƶ���
		 */
		Animation curIconAnim = AnimationUtils.loadAnimation(this,
				R.anim.rotatecurrentweather);
		currentIcon.setAnimation(curIconAnim);

		/**
		 * ���������ִ�����¶���
		 */
		if (icon == R.drawable.weather_cloudy) {
			AbsoluteLayout absLayout = (AbsoluteLayout) findViewById(R.id.imagesLayout);
			ImageView cloud01 = new ImageView(this);
			ImageView cloud02 = new ImageView(this);

			/**
			 * ����Ϊtrue����ʾ�ؼ�������ͼ����Դ�Ŀ�߱���
			 */
			cloud01.setAdjustViewBounds(true);
			cloud02.setAdjustViewBounds(true);
			cloud01.setImageResource(R.drawable.layer_cloud1);
			cloud02.setImageResource(R.drawable.layer_cloud2);

			/**
			 * ֻ�е�����setAdjustViewBounds(true)��ʱ�� 1) set adjustViewBounds to
			 * true 2) set maxWidth and maxHeight to �ɱ��� 3) set the height and
			 * width layout params to WRAP_CONTENT.
			 */
			cloud01.setMaxHeight(48);
			/**
			 * ������С�߶�
			 */
			cloud01.setMinimumHeight(48);
			cloud01.setMaxWidth(100);
			/**
			 * ������С���
			 */
			cloud01.setMinimumWidth(100);

			cloud02.setMaxHeight(58);
			cloud02.setMinimumHeight(58);
			cloud02.setMaxWidth(83);
			cloud02.setMinimumWidth(83);

			/**
			 * ���Ҷ���
			 */
			Animation leftAnim = AnimationUtils.loadAnimation(this,
					R.anim.translatecloudleft);
			Animation rightAnim = AnimationUtils.loadAnimation(this,
					R.anim.translatecloudright);

			// leftAnim.setRepeatCount(Animation.INFINITE);
			// rightAnim.setRepeatCount(Animation.INFINITE);

			cloud01.setAnimation(leftAnim);
			cloud02.setAnimation(rightAnim);

			absLayout.addView(cloud01);
			absLayout.addView(cloud02);

		}

		/**
		 * ���������ִ�����¶���
		 */
		if (icon == R.drawable.weather_rain) {
			AbsoluteLayout absLayout = (AbsoluteLayout) findViewById(R.id.imagesLayout);
			ImageView rain01 = new ImageView(this);
			ImageView rain02 = new ImageView(this);
			ImageView rain03 = new ImageView(this);
			ImageView rain04 = new ImageView(this);
			ImageView rain05 = new ImageView(this);
			ImageView drop01 = new ImageView(this);
			ImageView drop02 = new ImageView(this);
			ImageView drop03 = new ImageView(this);
			rain01.setAdjustViewBounds(true);
			rain02.setAdjustViewBounds(true);
			rain03.setAdjustViewBounds(true);
			rain04.setAdjustViewBounds(true);
			rain05.setAdjustViewBounds(true);
			drop01.setAdjustViewBounds(true);
			drop02.setAdjustViewBounds(true);
			drop03.setAdjustViewBounds(true);
			rain01.setImageResource(R.drawable.rain1);
			rain02.setImageResource(R.drawable.rain1);
			rain03.setImageResource(R.drawable.rain2);
			rain04.setImageResource(R.drawable.rain3);
			rain05.setImageResource(R.drawable.rain2);
			drop01.setImageResource(R.drawable.layer_drop1);
			drop02.setImageResource(R.drawable.layer_drop5);
			drop03.setImageResource(R.drawable.layer_drop7);

			LayoutParams lp01 = new LayoutParams(18, 30, 100, 150);
			LayoutParams lp02 = new LayoutParams(16, 33, 150, 140);
			LayoutParams lp03 = new LayoutParams(19, 30, 200, 150);

			Animation rain01Anim = AnimationUtils.loadAnimation(this,
					R.anim.translaterain01);
			Animation rain02Anim = AnimationUtils.loadAnimation(this,
					R.anim.translaterain02);
			Animation rain03Anim = AnimationUtils.loadAnimation(this,
					R.anim.translaterain03);
			Animation rain04Anim = AnimationUtils.loadAnimation(this,
					R.anim.translaterain04);
			Animation rain05Anim = AnimationUtils.loadAnimation(this,
					R.anim.translaterain05);

			rain01.setAnimation(rain01Anim);
			rain02.setAnimation(rain02Anim);
			rain03.setAnimation(rain03Anim);
			rain04.setAnimation(rain04Anim);
			rain05.setAnimation(rain05Anim);

			absLayout.addView(rain01);
			absLayout.addView(rain02);
			absLayout.addView(rain03);
			absLayout.addView(rain04);
			absLayout.addView(rain05);
			absLayout.addView(drop01, lp01);
			absLayout.addView(drop02, lp02);
			absLayout.addView(drop03, lp03);
		}
	}
}
