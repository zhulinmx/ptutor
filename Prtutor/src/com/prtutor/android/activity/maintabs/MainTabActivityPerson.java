package com.prtutor.android.activity.maintabs;

import com.google.prtutor.R;
import com.google.prtutor.RoleSelect;
import com.google.prtutor.SearchActivity;
import com.google.prtutor.dao.FavoriteDAO;
import com.google.prtutor.dao.UserDAO;
import com.prtutor.android.activity.order.MyComment;
import com.prtutor.android.activity.order.MyOrder;
import com.prtutor.android.activity.person.Mycollect;
import com.prtutor.android.activity.person.Mylove;
import com.prtutor.android.activity.person.PersonSetting;
import com.prtutor.android.activity.teacher.TeacherActivity;
import com.prtutor.android.view.ExpandableGridViewActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalBitmap;

public class MainTabActivityPerson extends Fragment
{
	private LinearLayout mineorder;
	private LinearLayout minefavorite;
	private LinearLayout mineinfo;
	private LinearLayout minecourse;
	private LinearLayout minestage;
	private LinearLayout minepingjia;
	private TextView tvname,tvphone,tvcollect;
	private ImageView ivicon;
	private Button btn_lgout;
	private UserDAO userDAO;
	FinalBitmap fb = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.activity_maintabs_person, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		userDAO = new UserDAO(getActivity());
		fb=FinalBitmap.create(getActivity());
		initView();
		initData();
		initEvents();

	}
	private void initView() {
		mineorder = (LinearLayout) getActivity().findViewById(R.id.mine_order);
		minefavorite = (LinearLayout) getActivity().findViewById(R.id.mine_favorite);
		mineinfo =  (LinearLayout) getActivity().findViewById(R.id.mine_info);
		minecourse =  (LinearLayout) getActivity().findViewById(R.id.mine_course);
		minepingjia =  (LinearLayout) getActivity().findViewById(R.id.mine_pingjia);
		tvname = (TextView) getActivity().findViewById(R.id.data_name);
		tvphone = (TextView) getActivity().findViewById(R.id.data_phone);
		tvcollect = (TextView) getActivity().findViewById(R.id.tv_collect_count);
		ivicon = (ImageView) getActivity().findViewById(R.id.data_icon);
		btn_lgout = (Button) getActivity().findViewById(R.id.mine_logout);
	}
	public void initEvents() {
		mineinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), PersonSetting.class);
				startActivity(intent);// 进入个人资料			
			}
		});
		mineorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), MyOrder.class);
				startActivity(intent);// 进入个人订单			
			}
		});
		minefavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), Mycollect.class);
				startActivity(intent);// 进入个人收藏			
			}
		});
		minecourse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), Mylove.class);
				startActivity(intent);// 进入评价管理			
			}
		});
		minepingjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), MyComment.class);
				startActivity(intent);// 进入评价管理			
			}
		});
		btn_lgout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), RoleSelect.class);
				startActivity(intent);// 进入评价管理	
			}
		});
	}
	public void initData(){
		SharedPreferences sp = getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
		String username = sp.getString("username", "").toString();
		Cursor cursorData = userDAO.queryUserName(username);
    	tvname.setText(username);
	    FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity());
	    tvcollect.setText(""+favoriteDAO.CountCollect(username));
        if (cursorData.moveToFirst()) {
        	tvphone.setText("手机:"+cursorData.getString(3));      	
        	fb.display(ivicon,cursorData.getString(0));
		}
	}
}

