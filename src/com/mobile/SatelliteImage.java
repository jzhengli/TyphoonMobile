package com.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class SatelliteImage extends Activity{
	ImageView imageview;
	int i=0;
	int[] satellitelist={R.drawable.satellite1,R.drawable.satellite2,R.drawable.satellite3,R.drawable.satellite4,R.drawable.satellite5,R.drawable.satellite6,R.drawable.satellite7,R.drawable.satellite8,R.drawable.satellite9,R.drawable.satellite10,R.drawable.satellite11,R.drawable.satellite12};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		this.setTitle(R.string.item4);
		
		imageview=(ImageView)findViewById(R.id.SatelliteImage);
		imageview.setImageResource(R.drawable.satellite12);		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "≤•∑≈ µ ±Œ¿–«‘∆Õº");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			new Thread(new ViewThread()).start();
			break;
		}
		return true;
	}
	Handler myHandler = new Handler()
    {
    	public void handleMessage(Message msg) {
    		if(msg.what==1)
    		{
    			imageview.setImageResource(satellitelist[i]);
    			System.out.println(i);
    			i++;
    		}
    	};
    };
    class ViewThread implements Runnable {
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				if (i < 12) {
					Message message = new Message();
					message.what = 1;
					SatelliteImage.this.myHandler.sendMessage(message);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}else{
					i=0;
					break;
				}
			}
		}
	}
}
