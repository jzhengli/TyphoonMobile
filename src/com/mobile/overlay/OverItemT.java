package com.mobile.overlay;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.OverlayItem;

public class OverItemT extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	private Drawable drawable;
	private Context mContext;

	public OverItemT(Drawable drawable, Context context) {
		super(boundCenterBottom(drawable));
		
		this.mContext = context;
		// TODO Auto-generated constructor stub
			
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mapOverlays.size();
	}

	public void addOverlay(OverlayItem overlayItem) {
		mapOverlays.add(overlayItem);
		this.populate();
	}

	protected boolean onTap(int i){
		Toast.makeText(this.mContext, mapOverlays.get(i).getSnippet(), Toast.LENGTH_SHORT).show();
		return true;
	}
}
