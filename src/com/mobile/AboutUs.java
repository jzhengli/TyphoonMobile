package com.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class AboutUs extends Activity{
	private ImageView imageView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		this.setTitle(R.string.item9);
		
		imageView=(ImageView)findViewById(R.id.SatelliteImage);
		imageView.setImageResource(R.drawable.aboutus);
	}
}
