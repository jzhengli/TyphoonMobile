package com.mobile.overlay;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class TyphoonPointItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	   
	   
	   public TyphoonPointItemizedOverlay(Drawable defaultMarker) {
	        super(boundCenter(defaultMarker));
	   }

	   @Override
	   protected OverlayItem createItem(int i) {
	      return mapOverlays.get(i);
	   }

	   @Override
	   public int size() {
	      return mapOverlays.size();
	   }
	   
	   
	   public void addOverlay(OverlayItem overlay) {
	      mapOverlays.add(overlay);
	       this.populate();
	   }
}
