package com.prtutor.android.activity.teacher;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.prtutor.R;
import com.google.prtutor.dao.OrderDAO;

public class OrderFragment extends Fragment {

    private ListView listView;
    private RadioGroup stateRadioGroup;
    private OrderDAO orderDAO;
	ArrayList<HashMap<String, Object>> listData;
	BaseAdapter adapter;
	String limtstate;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.teacher_order, container, false);
    }
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		orderDAO = new OrderDAO(getActivity());
		initView();
		initEvents();
		quyTutor("");
	}
    private void initView(){
    	listView =  (ListView) getActivity().findViewById(R.id.order_tutor_lv);
    	stateRadioGroup  = (RadioGroup) getActivity().findViewById(R.id.rg_state);    	
    }
	public void initEvents() {
		stateRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton tp = (RadioButton) getActivity().findViewById(checkedId);
				limtstate = tp.getText().toString();
				quyTutor(limtstate);
			}
		});
	}
	public void quyTutor(String lim){
		
		SharedPreferences sp = getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
        int teachId = sp.getInt("teachId", 0);
        Cursor cursor = orderDAO.queryOrderTr(teachId,lim);
		int columnsSize = cursor.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				map.put("id", cursor.getInt(0));
				map.put("odstage", cursor.getString(2));
				map.put("stuname", "学员:"+cursor.getString(3));
				map.put("img", cursor.getInt(4));
				map.put("createTime", cursor.getString(5));
				map.put("courseName", cursor.getString(6));	
			}
			listData.add(map);
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(getActivity(), listData, R.layout.item_list_order,
				new String[] { "courseName", "odstage", "createTime", "img", "stuname"},
				new int[] { R.id.list_course_or, R.id.list_order_desc, R.id.list_order_money, R.id.list_order_iv,R.id.list_order_time });
		listView.setAdapter(adapter);
	}
}
