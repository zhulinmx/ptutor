package com.prtutor.android.activity.teacher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import com.google.prtutor.R;

public class TeacherActivity extends FragmentActivity implements View.OnClickListener {

	private ResideMenu resideMenu;
	private TeacherActivity mContext;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemOrder;
	private ResideMenuItem itemCourse;
	private ResideMenuItem itemLoginout;
	private ResideMenuItem itemChangebg;
	private TextView tv_name;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacher_main);
		mContext = this;
		tv_name = (TextView) findViewById(R.id.tv_main_tutor_name);
		SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
        String tutorName = sp.getString("teachname", "");
        tv_name.setText(tutorName);
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new HomeFragment());
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setUse3D(true);
		resideMenu.setBackground(R.drawable.menu_background_star);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		itemChangebg = new ResideMenuItem(this, R.drawable.icon_settings, "切换背景");
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "主页");
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "我的");
		itemOrder = new ResideMenuItem(this, R.drawable.icon_tutor_order, "订单");
		itemCourse = new ResideMenuItem(this, R.drawable.icon_tutor_course, "课程");
		itemLoginout = new ResideMenuItem(this, R.drawable.icon_tutor_exit, "退出登录");
		
		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemOrder.setOnClickListener(this);
		itemCourse.setOnClickListener(this);
		itemLoginout.setOnClickListener(this);
		itemChangebg.setOnClickListener(this);

		resideMenu.addMenuItem(itemChangebg, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemOrder, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemCourse, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemLoginout, ResideMenu.DIRECTION_RIGHT);

		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == itemHome) {
			changeFragment(new HomeFragment());
		} else if (view == itemProfile) {
			changeFragment(new ProfileFragment());
		} else if (view == itemOrder) {
			changeFragment(new OrderFragment());
		} else if (view == itemCourse) {
			changeFragment(new SettingsFragment());
		} else if (view == itemLoginout) {
			finish();
		} else if (view == itemChangebg) {
     		resideMenu.setBackground(R.drawable.menu_background_sea);
		}

		resideMenu.closeMenu();
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// 打开菜单
			// Toast.makeText(mContext, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			// 关闭菜单
			// Toast.makeText(mContext, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}

	// What good method is to access resideMenu
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
}
