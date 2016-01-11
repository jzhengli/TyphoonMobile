package com.mobile;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RadarList extends Activity {
    /** Called when the activity is first created. */
	    private ListView list;
        private ArrayList<String> lvList;
        private ArrayAdapter<String> lvAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.setTitle(R.string.item5);
        
        list=(ListView)findViewById(R.id.ListView1);
        lvList = new ArrayList<String>();
        lvList.add("全国雷达图");
        lvList.add("南通雷达图");
        lvList.add("杭州雷达图");
        lvList.add("温州雷达图");
        lvList.add("厦门雷达图");
        lvAdapter = new ArrayAdapter<String>(this, R.layout.listviewitem,lvList);
        list.setAdapter(lvAdapter);
        list.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> arg0,
                    View arg1, int arg2, long arg3) {
            	Bundle b=new Bundle();
            	switch(arg2){
                case 0:
        			b.putString("content", "radar0");
        			Intent intent0 = new Intent(RadarList.this, RadarImage.class);
        			intent0.putExtras(b);
        			startActivity(intent0);
        			break;
                case 1:
        			b.putString("content", "radar1");
        			Intent intent1 = new Intent(RadarList.this, RadarImage.class);
        			intent1.putExtras(b);
        			startActivity(intent1);
        			break;
                case 2:
        			b.putString("content", "radar2");
        			Intent intent2 = new Intent(RadarList.this, RadarImage.class);
        			intent2.putExtras(b);
        			startActivity(intent2);
        			break;
                case 3:
        			b.putString("content", "radar3");
        			Intent intent3 = new Intent(RadarList.this, RadarImage.class);
        			intent3.putExtras(b);
        			startActivity(intent3);
        			break;
                case 4:
        			b.putString("content", "radar4");
        			Intent intent4 = new Intent(RadarList.this, RadarImage.class);
        			intent4.putExtras(b);
        			startActivity(intent4);
        			break;
                }
            }
        } );
    }   
}