package com.prtutor.android.activity.order;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.OrderDAO;
import com.google.prtutor.model.Tb_order;
import com.prtutor.android.view.HandyTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddOrder extends BaseActivity implements OnClickListener {
    
	private Button btn_order;
	private ImageButton order_back;
	private HandyTextView tv_order_name;
	private HandyTextView tv_order_course;
	private HandyTextView tv_order_price;
	private String username;
	private String courseName;
	private float price;
	private int courseId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
	    SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		username = sp.getString("username", "");
	    courseName = bundle.getString("courseName");
	    price = bundle.getFloat("price");
	    courseId = bundle.getInt("courseId");
		initViews();
		initEvents();
		
	}

	@Override
	protected void initViews() {
		btn_order = (Button) findViewById(R.id.btn_order);
		order_back = (ImageButton) findViewById(R.id.order_back);
		tv_order_name = (HandyTextView) findViewById(R.id.tv_order_name);
		tv_order_course = (HandyTextView) findViewById(R.id.tv_order_course);
		tv_order_price = (HandyTextView) findViewById(R.id.tv_order_price); 
		tv_order_name.setText(username);
		tv_order_course.setText(courseName);
		tv_order_price.setText("£¤"+price);
	}

	@Override
	protected void initEvents() {
		btn_order.setOnClickListener(this);
		order_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_order:
			Time time = new Time();
		    time.setToNow();
		    String currentTime = time.format("%y-%m-%d %H:%M:%S");
			Tb_order tb_order = new Tb_order(courseId,username,"ÒÑ±¨Ãû",currentTime);
			OrderDAO orderDAO = new OrderDAO(this);
			orderDAO.addOrder(tb_order);
			startActivity(MyOrder.class);
			break;
		case R.id.order_back:
			defaultFinish();
			break;		

		}
	}

}
