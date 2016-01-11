package com.mobile.forecast;

import java.util.regex.Pattern;
import com.mobile.R;
import android.text.format.Time;
public class ForecastUtil {
	/**
     * ����ÿ�����Ŀ�ʼʱ��
     */
    private static final int DAYTIME_BEGIN_HOUR = 8;

    /**
     * ����ÿ�����Ľ���ʱ��
     */
    private static final int DAYTIME_END_HOUR = 20;
    
	/**
	 * �жϵ�ǰʱ���Ƿ����ڰ���
     */
    public static boolean isDaytime() {
        Time time = new Time();
        time.setToNow();
        return (time.hour >= DAYTIME_BEGIN_HOUR && time.hour <= DAYTIME_END_HOUR);
    }
    
    private static final Pattern sIconStorm = Pattern.compile("(thunder|tstms)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconSnow = Pattern.compile("(snow|ice|frost|flurries)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconShower = Pattern.compile("(showers)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconSun = Pattern.compile("(sunny|breezy|clear)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconClouds = Pattern.compile("(cloud|overcast)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconPartlyCloudy = Pattern.compile("(partly_cloudy|mostly_sunny)", Pattern.CASE_INSENSITIVE);    
    private static final Pattern sIconMostCloudy = Pattern.compile("(most_cloudy)", Pattern.CASE_INSENSITIVE);    
    private static final Pattern sIconLightrain = Pattern.compile("(lightrain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconChanceOfRain = Pattern.compile("(chance_of_rain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconHeavyrain = Pattern.compile("(heavyrain)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconRain = Pattern.compile("(rain|storm)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconHaze = Pattern.compile("(haze)", Pattern.CASE_INSENSITIVE);
    private static final Pattern sIconFog = Pattern.compile("(fog)", Pattern.CASE_INSENSITIVE);    
    
    /**
     * ��ȡ��ʾ��ǰ����״̬��ͼ��
     * @param iconDescription  ����״̬ͼ�������
     * @return
     */
	public static int getForecastImage(String iconDescription){
		int icon = 0;
		boolean isDaytime = isDaytime();
		if (iconDescription == null){
        	icon = R.drawable.weather_sunny;
		}else if (sIconStorm.matcher(iconDescription).find()) {
            icon = isDaytime? R.drawable.weather_chancestorm : R.drawable.weather_chancestorm_n;
        } else if (sIconSnow.matcher(iconDescription).find()) {
            icon = isDaytime? R.drawable.weather_chancesnow : R.drawable.weather_chancesnow_n;
        } else if (sIconLightrain.matcher(iconDescription).find()) {
            icon = R.drawable.weather_lightrain;
        } else if (sIconShower.matcher(iconDescription).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconPartlyCloudy.matcher(iconDescription).find()) {
            icon = isDaytime ? R.drawable.weather_mostlysunny : R.drawable.weather_mostlysunny_n;
        } else if (sIconMostCloudy.matcher(iconDescription).find()) {
            icon = isDaytime ? R.drawable.weather_mostlycloudy : R.drawable.weather_mostlycloudy_n;
        } else if (sIconSun.matcher(iconDescription).find()) {
            icon = isDaytime ? R.drawable.weather_sunny : R.drawable.weather_sunny_n;
        } else if (sIconClouds.matcher(iconDescription).find()) {
            icon = R.drawable.weather_cloudy;
        } else if (sIconHeavyrain.matcher(iconDescription).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconRain.matcher(iconDescription).find()) {
            icon = R.drawable.weather_rain;
        } else if (sIconHaze.matcher(iconDescription).find()) {
            icon = R.drawable.weather_haze;
        } else if (sIconFog.matcher(iconDescription).find()) {
            icon = R.drawable.weather_fog;
        } else if (sIconChanceOfRain.matcher(iconDescription).find()) {
            icon = isDaytime ? R.drawable.weather_cloudyrain : R.drawable.weather_cloudyrain_n;
        } else {
        	icon = R.drawable.weather;
        }
		
		return icon;
	}
	
	/**
	 * ��ȡ��ʾ����״̬��ͼ�꣨�����ж��Ƿ���ʾ������
	 * @param iconDescription ����״̬ͼ�������
	 * @return
	 */
	public static int getCurrentForecastIcon(String iconDescription){
		int icon = 0;
		if (iconDescription == null){
			icon = R.drawable.weather_sunny;
		} else if (sIconClouds.matcher(iconDescription).find()){
			icon = R.drawable.weather_cloudy;
		} else if (sIconRain.matcher(iconDescription).find()){
			icon = R.drawable.weather_rain;
		}
		
		return icon;
	}
	
	/**
	 * ��ȡ��ʾԤ������״̬��ͼ��
	 * @param iconDescription ����״̬ͼ�������
	 * @return
	 */
	public static int getDetailForecastIcon(String iconDescription){
		int icon = 0;
		
		if (iconDescription == null) {
			icon = R.drawable.sun;
		} else if (sIconPartlyCloudy.matcher(iconDescription).find()) {
            icon = R.drawable.mostlysunny;
        } else if (sIconSun.matcher(iconDescription).find()) {
			icon = R.drawable.sun;
		} else if (sIconClouds.matcher(iconDescription).find()) {
			icon = R.drawable.cloudy;
		} else if (sIconLightrain.matcher(iconDescription).find()) {
			icon = R.drawable.lightrain;
		} else if (sIconStorm.matcher(iconDescription).find()) {
			icon = R.drawable.storm;
		} else if (sIconChanceOfRain.matcher(iconDescription).find()) {
            icon = R.drawable.cloudyrain;
        } else if (sIconRain.matcher(iconDescription).find()) {
			icon = R.drawable.rain;
		} else if (sIconFog.matcher(iconDescription).find()) {
			icon = R.drawable.fog;
		} else if (sIconSnow.matcher(iconDescription).find()) {
			icon = R.drawable.snow;
		}
		
		return icon;
	}
}
