package com.prtutor.android.activity.maintabs;

import com.google.prtutor.LoginActivity;
import com.google.prtutor.R;
import com.prtutor.android.view.ExpandableGridViewActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainTabActivity extends FragmentActivity implements OnClickListener {

	private LinearLayout mTabBtnHp;
	private LinearLayout mTabBtnCr;
	private LinearLayout mTabBtnFd;
	private LinearLayout mTabBtnPr;

	private ImageButton mImgHp;
	private ImageButton mImgCr;
	private ImageButton mImgFd;
	private ImageButton mImgPr;
	
	private TextView mtvHp;
	private TextView mtvCr;
	private TextView mtvFd;
	private TextView mtvPr;

	private Fragment mTabHp;
	private Fragment mTabCr;
	private Fragment mTabFd;
	private Fragment mTabPr;

	private TextView childgrid;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_maintabs);
		initView();
		initEvent();
		setSelect(0);//默认home界面显示
		/*
		 * Intent intent = getIntent(); Bundle bundle = intent.getExtras();
		 * String str = bundle.getString("str"); childgrid = (TextView)
		 * findViewById(R.id.child_Grid); childgrid.setText(str);
		 */
	}

	private void initEvent() {

		mTabBtnHp.setOnClickListener(this);
		mTabBtnCr.setOnClickListener(this);
		mTabBtnFd.setOnClickListener(this);
		mTabBtnPr.setOnClickListener(this);

	}

	private void initView() {
		mTabBtnHp = (LinearLayout) findViewById(R.id.id_tab_homepage);
		mTabBtnCr = (LinearLayout) findViewById(R.id.id_tab_course);
		mTabBtnFd = (LinearLayout) findViewById(R.id.id_tab_find);
		mTabBtnPr = (LinearLayout) findViewById(R.id.id_tab_person);

		mImgHp = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgCr = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgFd = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgPr = (ImageButton) findViewById(R.id.id_tab_settings_img);
		
		mtvHp = (TextView) findViewById(R.id.id_tab_weixin_tv);
		mtvCr = (TextView) findViewById(R.id.id_tab_frd_tv);
		mtvFd = (TextView) findViewById(R.id.id_tab_address_tv);
		mtvPr = (TextView) findViewById(R.id.id_tab_settings_tv);
	}

	private void setSelect(int i) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i) {
		case 0:
			if (mTabHp == null) {
				mTabHp = new MainTabActivityHomePage();
				transaction.add(R.id.id_content, mTabHp);
			} else {
				transaction.show(mTabHp);
			}
			mImgHp.setImageResource(R.drawable.tab_weixin_pressed);
			mtvHp.setTextColor(mtvHp.getResources().getColor(R.color.orange));
			break;
		case 1:
			if (mTabCr == null) {
				mTabCr = new MainTabActivityCourse();
				transaction.add(R.id.id_content, mTabCr);
			} else {
				transaction.show(mTabCr);

			}
			mImgCr.setImageResource(R.drawable.tab_find_frd_pressed);
			mtvCr.setTextColor(mtvCr.getResources().getColor(R.color.orange));
			
			break;
		case 2:
			if (mTabFd == null) {
				mTabFd = new MainTabActivityFind();
				transaction.add(R.id.id_content, mTabFd);
			} else {
				transaction.show(mTabFd);
			}
			mImgFd.setImageResource(R.drawable.tab_address_pressed);
			mtvFd.setTextColor(mtvFd.getResources().getColor(R.color.orange));
			break;
		case 3:
			if (mTabPr == null) {
				mTabPr = new MainTabActivityPerson();
				transaction.add(R.id.id_content, mTabPr);
			} else {
				transaction.show(mTabPr);
			}
			mImgPr.setImageResource(R.drawable.tab_settings_pressed);
			mtvPr.setTextColor(mtvPr.getResources().getColor(R.color.orange));
			break;

		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		if (mTabHp != null) {
			transaction.hide(mTabHp);
		}
		if (mTabCr != null) {
			transaction.hide(mTabCr);
		}
		if (mTabFd != null) {
			transaction.hide(mTabFd);
		}
		if (mTabPr != null) {
			transaction.hide(mTabPr);
		}
	}

	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.id_tab_homepage:
			setSelect(0);
			break;
		case R.id.id_tab_course:
			setSelect(1);
			break;
		case R.id.id_tab_find:
			setSelect(2);
			break;
		case R.id.id_tab_person:
			setSelect(3);
			break;

		default:
			break;
		}
	}

	// 切换图片和字至暗色

	private void resetImgs() {
		mImgHp.setImageResource(R.drawable.tab_weixin_normal);
		mImgCr.setImageResource(R.drawable.tab_find_frd_normal);
		mImgFd.setImageResource(R.drawable.tab_address_normal);
		mImgPr.setImageResource(R.drawable.tab_settings_normal);
		mtvHp.setTextColor(mtvHp.getResources().getColor(R.color.gray));
		mtvCr.setTextColor(mtvCr.getResources().getColor(R.color.gray));
		mtvFd.setTextColor(mtvFd.getResources().getColor(R.color.gray));
		mtvPr.setTextColor(mtvPr.getResources().getColor(R.color.gray));
	}

}
