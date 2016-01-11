package com.mobile;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mobile.overlay.MyPositionItemizedOverlay;

public class MyLocation extends MapActivity{
	MapView mapView;
	MapController controller;
	
	List<Overlay> mapOverlays;
	Drawable drawable;
	MyPositionItemizedOverlay itemizedOverlay;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.typhoonview);
		this.setTitle(R.string.item2);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		controller = mapView.getController();		
		double lat = 0.0;
		double lng =0.0;
		if (Splash.location != null) {
			lat = Splash.location.getLatitude();
		    lng = Splash.location.getLongitude();
		    GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
			controller.animateTo(point);
			controller.setZoom(6);
			
			mapOverlays = mapView.getOverlays();
			drawable = this.getResources().getDrawable(R.drawable.location1);
			itemizedOverlay = new MyPositionItemizedOverlay(drawable);
			OverlayItem overlayitem = new OverlayItem(point, "", "");
			itemizedOverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedOverlay);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					controller.setZoom(12);
				}

			}, 3000);
		} 
		else{
			Toast toast=Toast.makeText(getApplicationContext(), "无法定位位置", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	@Override
	// 是否显示路径
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "查看附近救援队");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			GeoPoint pointcenter=new GeoPoint((int) (29.885900 * 1E6),(int) (122.334137 * 1E6));
			controller.animateTo(pointcenter);
			controller.setZoom(12);
			GeoPoint point1=new GeoPoint((int) (29.9476 * 1E6),(int) (122.2984 * 1E6));
			GeoPoint point2=new GeoPoint((int) (29.9314 * 1E6),(int) (122.398 * 1E6));
			Drawable drawable = this.getResources().getDrawable(R.drawable.help);
			MyPositionItemizedOverlay pointoverlay=new MyPositionItemizedOverlay(drawable);
			pointoverlay.addOverlay(new OverlayItem(point1,"",""));
			pointoverlay.addOverlay(new OverlayItem(point2,"",""));
			mapView.getOverlays().add(pointoverlay);
			break;
		}
		return true;
	}
}
