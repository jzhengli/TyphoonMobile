package com.mobile.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobile.forecast.CurrentWeatherCondition;
import com.mobile.forecast.ForecastWeatherCondition;
import com.mobile.forecast.WeatherSet;

public class GoogleWeatherHandler extends DefaultHandler {
    private  String tagName;String cCondition,cTemp_f;
    private  boolean is_Current_Condition = false;
    private  boolean is_Forecast_Condition = false;
    private  WeatherSet mySet;
    private int count=0;
 	public GoogleWeatherHandler(WeatherSet set){
		mySet = set;
	}
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attr) throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("current_conditions")) {
			is_Current_Condition = true;
			mySet.setCurrentWeatherCondition(new CurrentWeatherCondition());
		} else if (localName.equals("forecast_conditions")) {
			is_Forecast_Condition = true;
			mySet.getForecastWeathes().add(new ForecastWeatherCondition());
		} else {
			String value = attr.getValue("data");
			if (localName.equals("day_of_week"))
				mySet.getLastForecastCondition().setForecastDate(value);
			else if (localName.equals("low"))
				mySet.getLastForecastCondition().setLow(value);
			else if (localName.equals("high"))
				mySet.getLastForecastCondition().setHigh(value);
			else if (localName.equals("icon")) {
				if (is_Current_Condition)
					mySet.getCurrentWeatherCondition().setCurrentImagePath(
							value);
				if (is_Forecast_Condition)
					mySet.getLastForecastCondition()
							.setForecastImagePath(value);
			} else if (localName.equals("condition")) {
				if (is_Current_Condition)
					mySet.getCurrentWeatherCondition().setCurrentCondition(
							value);
				else if (is_Forecast_Condition) {
					mySet.getLastForecastCondition()
							.setForecastCondition(value);
					is_Forecast_Condition = false;
				}
			} else if (localName.equals("humidity")) {
				if (is_Current_Condition)
					mySet.getCurrentWeatherCondition()
							.setCurrentHumidity(value);
			} else if (localName.equals("temp_c")) {
				if (is_Current_Condition)
					mySet.getCurrentWeatherCondition().setCurrentTemperature(
							value);
			} else if (localName.equals("wind_condition")) {
				if (is_Current_Condition) {
					mySet.getCurrentWeatherCondition().setCurrentWind(value);
					is_Current_Condition = false;
				}
			}
		}
	}
}
