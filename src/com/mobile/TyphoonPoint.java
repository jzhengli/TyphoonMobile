package com.mobile;

import com.google.android.maps.GeoPoint;

public class TyphoonPoint {
	private double latitude;    //γ��
	private double longtitude;  //����
	private int atopressure;    //������ѹ
	private int airspeed;       //������
	private int status;          //״̬
	private GeoPoint point;     //���ĵ�
	
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
