package com.mobile.forecast;

import com.mobile.Splash;

public class CurrentWeatherCondition {
	private String currentHumidity;
	private String currentCondition;
	private String currentWind;
	public String currentImage;
	public String currentTemperature;

	public void setCurrentHumidity(String s) {
		currentHumidity = s;
	}

	public void setCurrentCondition(String s) {
		currentCondition = s;
	}

	public void setCurrentImagePath(String s) {
		currentImage = s;
	}

	public void setCurrentTemperature(String s) {
		currentTemperature = s;
	}

	public void setCurrentWind(String s) {
		currentWind = s;
	}
	
	public String getCity(){
		return Splash.usercity+"£¬"+Splash.userprovice;
	}
	public String getCondition(){
		return currentCondition;
	}
	public String getHumidity(){
		return currentHumidity;
	}
	public String getWindCondition(){
		return currentWind;
	}
	public String getTempC(){
		return currentTemperature;
	}
	public String getimage(){
		return currentImage;
	}
}
