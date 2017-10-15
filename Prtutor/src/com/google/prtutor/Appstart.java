package com.google.prtutor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class Appstart extends Activity {
	
	@Override
	public void onCreate(Bundle savesInstanceState){
		super.onCreate(savesInstanceState);
		setContentView(R.layout.activity_appstart);
		new Handler().postDelayed(new Runnable(){
			public void run(){
				Intent intent = new Intent(Appstart.this,RoleSelect.class);
				startActivity(intent);
				Appstart.this.finish();
			}
		},2000); //handler—”≥Ÿ¡Ω√Î																		
	}
}
