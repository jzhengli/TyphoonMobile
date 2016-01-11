package com.mobile.overlay;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class TyphoonEyeItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	   
	private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG  
	            | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;  
	   private ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
	   private Bitmap bitmap;
	   
	   public TyphoonEyeItemizedOverlay(Drawable defaultMarker) {
		   super(boundCenter(defaultMarker));
	   }
	   @Override
	   protected OverlayItem createItem(int i) {
	      return overlayItems.get(i);
	   }

	   @Override
	   public int size() {
	      return overlayItems.size();
	   }
	   
	   
	   public void addOverlay(OverlayItem overlay) {
		   overlayItems.add(overlay);
	       this.populate();
	   }
	   
	   public void draw(Canvas canvas, MapView mapView, boolean shadow) {  
  	        if (!shadow) {  
  	            canvas.save(LAYER_FLAGS);  
  	  
  	            Projection projection = mapView.getProjection();  
  	            int size = overlayItems.size();  
 	            Point point = new Point();
  	            
  	            Paint paint=new Paint();  	             
  	            paint.setColor(Color.BLUE);
  	            paint.setAlpha(0x40);
 	            OverlayItem overLayItem;  
  	  
  	            for (int i = 0; i < size; i++) {  
  	                overLayItem = overlayItems.get(i);  
  	  
  	                Drawable marker = overLayItem.getMarker(0);  
 	                // marker.getBounds()  
  	                /* 象素点取得转换 */  
  	                projection.toPixels(overLayItem.getPoint(), point);  
  	  
  	                if (marker != null) {  
  	                    boundCenter(marker);  
  	                }  
  	                /* 圆圈 */  
  	                	canvas.drawCircle(point.x, point.y, 40, paint); 
  	            }  
  	  
  	            canvas.restore();  
  	        }  
  	        super.draw(canvas, mapView, shadow);  
  	    } 


	}

