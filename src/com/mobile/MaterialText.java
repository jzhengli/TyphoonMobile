package com.mobile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MaterialText extends Activity {
	private TextView textview;
	private SQLiteDatabase database;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		this.setTitle(R.string.item6);

		textview = (TextView) findViewById(R.id.textview);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		String title=b.getString("title");
		database = SQLiteDatabase.openOrCreateDatabase(Splash.database, null);
		Cursor cur = database.rawQuery(
				"SELECT content from materials WHERE title=" + "'"+title+"'", null);
		while(cur.moveToNext()){
			String source=cur.getString(0);
			textview.setText(Html.fromHtml(source));
		}
		cur.close();
		database.close();
	}
	
	
}
