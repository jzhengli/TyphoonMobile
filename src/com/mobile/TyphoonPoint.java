package com.mobile;

import com.google.android.maps.GeoPoint;

public class TyphoonPoint {
	private double latitude;    //纬度
	private double longtitude;  //经度
	private int atopressure;    //中心气压
	private int airspeed;       //最大风速
	private int status;          //状态
	private GeoPoint point;     //中心点
	
	public TyphoonPoint(double latitude,double longtitude,int atopressure,int airspeed,int status){
		this.latitude=latitude;
		this.longtitude=longtitude;
		this.atopressure=atopressure;
		this.airspeed=airspeed;
		this.status=status;
	}
	
	public double getlatitude(){
		return latitude;
	}
	public double getlongtitude(){
		return longtitude;
	}
	public int getatopressure(){
		return atopressure;
	}
	public int getairspeed(){
		return airspeed;
	}
	public int getstatus(){
		return status;
	}
	public GeoPoint getpoint(){
		point=new GeoPoint((int) (latitude * 1E6),(int) (longtitude * 1E6));
		return point;
	}
}
