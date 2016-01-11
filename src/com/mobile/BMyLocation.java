package com.mobile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.RouteOverlay;



import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.mobile.overlay.OverItemT;


public class BMyLocation extends MapActivity {
	BMapManager mBMapMan = null;
	static MapView mMapView = null;
	GeoPoint point, point1, point2;
	MKSearch mSearch = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylocationview);
		this.setTitle("我的位置");
		


		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("835D121604F9E2BCF1D3F1267ED6C2E28E2EB55E", null);
		super.initMapActivity(mBMapMan);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件

		if (Splash.location != null) {

			MapController mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
			point = new GeoPoint(
					(int) (Splash.location.getLatitude() * 1E6),
					(int) (Splash.location.getLongitude() * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度
																	// (度 * 1E6)
			mMapController.setCenter(point); // 设置地图中心点
			mMapController.setZoom(6); // 设置地图zoom级别
			
			//ArrayList<GeoPoint> lpoint = new ArrayList<GeoPoint>();
			//lpoint.add(point);
			Drawable drawable = this.getResources().getDrawable(R.drawable.location1);
			OverItemT overitem = new OverItemT(drawable,this);
			overitem.addOverlay(new OverlayItem(point, "", "舟山市合兴村                                                    北纬" + Splash.location.getLatitude() + "° 东经" + Splash.location.getLongitude() + "°"));
			mMapView.getOverlays().add(overitem);
			
			new Handler().postDelayed(new Runnable() {

				public void run() {
					mMapView.getController().setZoom(12);
				}

			}, 2000);

			//路径检索时间监听初始化
			mSearch = new MKSearch();
			mSearch.init(mBMapMan, new MKSearchListener(){

				public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				}
				public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {				
				}

				public void onGetDrivingRouteResult(MKDrivingRouteResult res,
						int error) {
					// TODO Auto-generated method stub
					if (error != 0 || res == null) {
						Toast.makeText(BMyLocation.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
						return;
					}
					RouteOverlay routeOverlay = new RouteOverlay(BMyLocation.this,mMapView);
					
					routeOverlay.setData(res.getPlan(0).getRoute(0));
					mMapView.getOverlays().add(routeOverlay);
				}
				public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {	
				}
				public void onGetTransitRouteResult(MKTransitRouteResult arg0,int arg1) {	
				}
				public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,int arg1) {
				}});
			
			
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "无法定位位置",
					Toast.LENGTH_LONG);
			toast.show();
		}

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "查看附近安全区域");
		menu.add(0,2,0,"计算最优撤离路径");
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			GeoPoint pointcenter=new GeoPoint((int) (29.885900 * 1E6),(int) (122.1 * 1E6));
			mMapView.getController().animateTo(pointcenter);
			mMapView.getController().setZoom(10);
			
			point1=new GeoPoint((int) (30.016 * 1E6),(int) (122.1 * 1E6));
			point2=new GeoPoint((int) (29.896944 * 1E6),(int) (121.846111 * 1E6));
			Drawable drawable = this.getResources().getDrawable(R.drawable.help);
			OverItemT overitem = new OverItemT(drawable,this);
			overitem.addOverlay(new OverlayItem(point1,"","舟山市定海区                                                    北纬 30.016°"+ "东经 122.1°"));
			overitem.addOverlay(new OverlayItem(point2,"","宁波市北仑区                                                    北纬 29.897°"+ "东经 121.8°"));
			mMapView.getOverlays().add(overitem);
			break;
		case 2:
			MKPlanNode start = new MKPlanNode();
			start.pt = new GeoPoint((int)(Splash.location.getLatitude() * 1E6),(int)(Splash.location.getLongitude() * 1E6));
			MKPlanNode end = new MKPlanNode();
			end.pt = new GeoPoint((int)(30.016 * 1E6),(int)(122.1 * 1E6));
			
			mSearch.drivingSearch(null, start, null, end);
			mMapView.getController().animateTo(new GeoPoint((int)(29.8 *1E6),(int)(122.26 * 1E6)));
			mMapView.getController().setZoom(11);
			System.out.println("绘制路径");
		}
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

}