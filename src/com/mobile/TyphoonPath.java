package com.mobile;

import java.util.List;
import java.util.Vector;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mobile.overlay.LineOverlay;
import com.mobile.overlay.TyphoonEyeItemizedOverlay;
import com.mobile.overlay.TyphoonPointItemizedOverlay;

public class TyphoonPath extends MapActivity {
	MapView mapView;
	List<Overlay> overlays;
	Drawable drawable,drawable1,drawable2;
	MapController mapController;
	GeoPoint initialpoint;
	TyphoonPointItemizedOverlay typhoonPointItemizedOverlay1,typhoonPointItemizedOverlay2;
	TyphoonEyeItemizedOverlay typhoonEyeItemizedOverlay;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.typhoonview);
		setProgressBarIndeterminateVisibility(true);
		
		this.setTitle(R.string.item1);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		
		initialpoint = new GeoPoint((int) (31.11 * 1E6), (int) (121.29 * 1E6));
		mapController = mapView.getController();		
		drawable = this.getResources().getDrawable(R.drawable.typhooneye);
		drawable1= this.getResources().getDrawable(R.drawable.red);
		drawable2= this.getResources().getDrawable(R.drawable.blue);
		typhoonPointItemizedOverlay1=new TyphoonPointItemizedOverlay(drawable1);
		typhoonPointItemizedOverlay2=new TyphoonPointItemizedOverlay(drawable2);
		typhoonEyeItemizedOverlay=new TyphoonEyeItemizedOverlay(drawable);
		mapController.animateTo(initialpoint);
		mapController.setZoom(3);
		
		myHandler.postDelayed(viewThread, 3000);	
	}
	
	Handler myHandler = new Handler();
	
	Runnable viewThread =new Runnable() {
		public void run() {	
			mapController.animateTo(initialpoint);
			mapController.setZoom(5);
			final View popView = View.inflate(TyphoonPath.this, R.layout.overlay_pop, null);
			mapView.addView(popView, new MapView.LayoutParams(
					MapView.LayoutParams.WRAP_CONTENT,
					MapView.LayoutParams.WRAP_CONTENT, null,
					MapView.LayoutParams.BOTTOM_CENTER));
			popView.setVisibility(View.GONE);
			final ItemizedOverlay.OnFocusChangeListener onFocusChangeListener = new ItemizedOverlay.OnFocusChangeListener() {
				public void onFocusChanged(ItemizedOverlay overlay,
						OverlayItem newFocus) {
					// 创建气泡窗口
					if (popView != null) {
						popView.setVisibility(View.GONE);
					}
					if (newFocus != null) {
						MapView.LayoutParams geoLP = (MapView.LayoutParams) popView
								.getLayoutParams();
						geoLP.point = newFocus.getPoint();// 这行用于popView的定位
						TextView title = (TextView) popView
								.findViewById(R.id.map_bubbleTitle);
						title.setText(newFocus.getTitle());
						TextView desc = (TextView) popView
								.findViewById(R.id.map_bubbleText);
						if (newFocus.getSnippet() == null
								|| newFocus.getSnippet().length() == 0) {
							desc.setVisibility(View.GONE);
						} else {
							desc.setVisibility(View.VISIBLE);
							desc.setText(newFocus.getSnippet());
						}
						mapView.updateViewLayout(popView, geoLP);
						popView.setVisibility(View.VISIBLE);
						//popView.getBackground().setAlpha(200);   //气泡透明度
					}
				}
			};
			//台风数据
			Vector<TyphoonPoint> allPoints=new Vector<TyphoonPoint>();
			allPoints.add(new TyphoonPoint(11.7,135,998,18,0));
			allPoints.add(new TyphoonPoint(12.8,133,990,23,0));
			allPoints.add(new TyphoonPoint(14.8,132.9,990,23,0));
			allPoints.add(new TyphoonPoint(15.8,133.7,980,30,0));
			allPoints.add(new TyphoonPoint(16.8,132.6,925,55,0));
			allPoints.add(new TyphoonPoint(17.7,133.5,935,50,0));
			allPoints.add(new TyphoonPoint(18.8,133.7,935,50,0));
			allPoints.add(new TyphoonPoint(19.8,134.1,940,48,0));
			allPoints.add(new TyphoonPoint(21.4,134.2,945,45,0));
			allPoints.add(new TyphoonPoint(22.8,133.1,940,48,0));
			allPoints.add(new TyphoonPoint(23.8,133.3,930,52,0));
			allPoints.add(new TyphoonPoint(24.8,128.8,950,45,0));
			allPoints.add(new TyphoonPoint(25.4,127.5,950,45,0));
			allPoints.add(new TyphoonPoint(26.9,126,950,45,0));
			allPoints.add(new TyphoonPoint(27.9,125.5,950,45,0));
			allPoints.add(new TyphoonPoint(28.6,125,960,40,0));
			allPoints.add(new TyphoonPoint(29.7,124.5,960,40,1));
			allPoints.add(new TyphoonPoint(33.6,124,965,38,1));
			allPoints.add(new TyphoonPoint(35.6,123.5,975,30,1));
			allPoints.add(new TyphoonPoint(38.3,124.1,980,25,1));
			allPoints.add(new TyphoonPoint(40.1,125.1,990,20,1));
			allPoints.add(new TyphoonPoint(43.8,125.6,995,13,1));
			
			for(int i=0;i<allPoints.size();i++){
				if(allPoints.get(i).getstatus()==0){
				    typhoonPointItemizedOverlay1.addOverlay(new OverlayItem(allPoints.get(i).getpoint(),"最大风速："+allPoints.get(i).getairspeed()+"米/秒","中心气压："+allPoints.get(i).getatopressure()+"百帕"));
				}else{
					typhoonPointItemizedOverlay2.addOverlay(new OverlayItem(allPoints.get(i).getpoint(),"最大风速："+allPoints.get(i).getairspeed()+"米/秒","中心气压："+allPoints.get(i).getatopressure()+"百帕"));
				}
			}
			typhoonEyeItemizedOverlay.addOverlay(new OverlayItem(allPoints.get(16).getpoint(),"最大风速："+allPoints.get(16).getairspeed()+"米/秒","中心气压："+allPoints.get(16).getatopressure()+"百帕"));
			typhoonPointItemizedOverlay1.setOnFocusChangeListener(onFocusChangeListener);
			typhoonPointItemizedOverlay2.setOnFocusChangeListener(onFocusChangeListener);
			typhoonEyeItemizedOverlay.setOnFocusChangeListener(onFocusChangeListener);
			overlays = mapView.getOverlays();
			overlays.add(new LineOverlay(allPoints));
			overlays.add(typhoonPointItemizedOverlay1);
			overlays.add(typhoonPointItemizedOverlay2);
			overlays.add(typhoonEyeItemizedOverlay);
			setProgressBarIndeterminateVisibility(false);
		}
	};
	
	@Override
	// 是否显示路径
	protected boolean isRouteDisplayed() {
		return false;
	}
}
