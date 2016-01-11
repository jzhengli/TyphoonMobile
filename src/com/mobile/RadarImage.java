package com.mobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ZoomControls;

public class RadarImage extends Activity{
	private ImageView imageView;
	private LinearLayout layout1;  
    private ZoomControls zoom;  
    private int id=0;  
    private int displayWidth;  
    private int displayHeight;  
    private float scaleWidth = 1;  
    private float scaleHeight = 1;  
    private Bitmap bmp;  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		
		/*layout1 = (LinearLayout)findViewById(R.id.layout1);  
        //取得屏幕分辨率大小  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        displayWidth = dm.widthPixels;  
        //屏幕高度减去zoomControls的高度  
        displayHeight = dm.heightPixels;
        zoom = (ZoomControls)findViewById(R.id.zoomcontrol);
        zoom.setIsZoomInEnabled(true);  
        zoom.setIsZoomOutEnabled(true); */
		imageView=(ImageView)findViewById(R.id.SatelliteImage);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		if(b.getString("content")!=null){
			if(b.getString("content").equals("radar0")){
				this.setTitle("全国雷达图");
				imageView.setImageResource(R.drawable.radar0);
				bmp=BitmapFactory.decodeResource(getResources(), R.drawable.radar0);
			}else if(b.getString("content").equals("radar1")){
				this.setTitle("南通雷达图");
				imageView.setImageResource(R.drawable.radar1);
				bmp=BitmapFactory.decodeResource(getResources(), R.drawable.radar1);
			}else if(b.getString("content").equals("radar2")){
				this.setTitle("杭州雷达图");
				imageView.setImageResource(R.drawable.radar2);
				bmp=BitmapFactory.decodeResource(getResources(), R.drawable.radar2);
			}else if(b.getString("content").equals("radar3")){
				this.setTitle("温州雷达图");
				imageView.setImageResource(R.drawable.radar3);
				bmp=BitmapFactory.decodeResource(getResources(), R.drawable.radar3);
			}else if(b.getString("content").equals("radar4")){
				this.setTitle("厦门雷达图");
				imageView.setImageResource(R.drawable.radar4);
				bmp=BitmapFactory.decodeResource(getResources(), R.drawable.radar4);
			}
		}
		/*//图片放大  
        zoom.setOnZoomInClickListener(new OnClickListener()  
        {  
            public void onClick(View v)  
            {  
                int bmpWidth = bmp.getWidth();  
                int bmpHeight = bmp.getHeight();  
                //设置图片放大但比例  
                double scale = 1.25;  
                //计算这次要放大的比例  
                scaleWidth = (float)(scaleWidth*scale);  
                scaleHeight = (float)(scaleHeight*scale);  
                //产生新的大小但Bitmap对象  
                Matrix matrix = new Matrix();  
                matrix.postScale(scaleWidth, scaleHeight);  
                Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true);  
                imageView.setImageBitmap(resizeBmp);  
  
            }  
        });  
      //图片减小  
        zoom.setOnZoomOutClickListener(new OnClickListener()  
        {  
  
            public void onClick(View v) {  
                int bmpWidth = bmp.getWidth();  
                int bmpHeight = bmp.getHeight();  
                //设置图片放大但比例  
                double scale = 0.8;  
                //计算这次要放大的比例  
                scaleWidth = (float)(scaleWidth*scale);  
                scaleHeight = (float)(scaleHeight*scale);  
                //产生新的大小但Bitmap对象  
                Matrix matrix = new Matrix();  
                matrix.postScale(scaleWidth, scaleHeight);  
                Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,bmpHeight,matrix,true);  
                imageView.setImageBitmap(resizeBmp);  
            }  
              
        });  */
		
	}
}
