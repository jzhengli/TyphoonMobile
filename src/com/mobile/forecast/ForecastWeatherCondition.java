package com.mobile.forecast;

public class ForecastWeatherCondition {
    private String forecastCondition;
    public  String  forecastImage;
    public  String  low;
    public  String  high;
    public  String  forecastDate;
    
    public void setForecastDate(String s){
      	 forecastDate = s;
       }
    public void setLow(String s){
     	 low = s;
      }
    public void setHigh(String s){
     	 high = s;
      }
    public void setForecastCondition(String s){
   	 forecastCondition = s;
    }
    public void setForecastImagePath(String s){
   	 forecastImage = s;
    }
    
    public String getforecastCondition(){
    	return forecastCondition;
    }
    
    public String getTempC(){
    	return low+"бу"+"/"+high+"бу";
    }
    
    
    public String getforecastDate(){
    	return forecastDate;
    }
    public String getimage(){
    	return forecastImage;
    }
}
