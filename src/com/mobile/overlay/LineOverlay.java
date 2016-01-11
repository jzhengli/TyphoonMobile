package com.mobile.overlay;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.mobile.TyphoonPoint;

public class LineOverlay extends Overlay {
	private Vector<TyphoonPoint> points;

	public LineOverlay() {

	}

	public LineOverlay(Vector<TyphoonPoint> points) {
		this.points = points;
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		Paint paint1 = new Paint(); 
		paint1.setColor(Color.RED);
		paint1.setStyle(Paint.Style.FILL_AND_STROKE);
		paint1.setStrokeWidth(2);
		
		Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG); 
		paint2.setColor(Color.BLUE);
		paint2.setStyle(Paint.Style.STROKE);
		paint2.setStrokeWidth(2);
		PathEffect mEffects =  new DashPathEffect(new float[] {5, 5, 5, 5}, 1); 
		paint2.setPathEffect(mEffects);
		
		for (int i = 0; i < points.size() - 1; i++) {

			Point beginPoint = new Point();
			Point endPoint = new Point();
			Path path = new Path();
			Projection projection = mapView.getProjection(); 
			projection.toPixels(points.get(i).getpoint(), beginPoint);
			projection.toPixels(points.get(i + 1).getpoint(), endPoint);
			path.moveTo(endPoint.x, endPoint.y);
			path.lineTo(beginPoint.x, beginPoint.y);
			if(points.get(i).getstatus()==0)
			{
				canvas.drawPath(path, paint1);
			}
			else
				canvas.drawPath(path, paint2);
			canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		}
	}
}
