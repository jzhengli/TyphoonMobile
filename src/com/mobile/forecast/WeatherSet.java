package com.mobile.forecast;

import java.util.ArrayList;
import java.util.List;

public class WeatherSet {
	CurrentWeatherCondition cwc = new CurrentWeatherCondition();
	List<ForecastWeatherCondition> myForecastWeathers = new ArrayList<ForecastWeatherCondition>();
    public void setCurrentWeatherCondition(CurrentWeatherCondition tcwc)
    {
    	cwc = tcwc;
    }
	public CurrentWeatherCondition getCurrentWeatherCondition()
	{
		return cwc;
	}
	public List<ForecastWeatherCondition> getForecastWeathes()
	{
		return myForecastWeathers;
	}
	//�������һ��Ԥ������
	public ForecastWeatherCondition getLastForecastCondition()
	{
		return myForecastWeathers.get(myForecastWeathers.size()-1);
	}
}
