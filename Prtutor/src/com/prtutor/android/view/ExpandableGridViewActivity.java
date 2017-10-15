package com.prtutor.android.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.prtutor.R;
import com.google.prtutor.BaseActivity;
import com.google.prtutor.Model;
import com.google.prtutor.adapter.ExpandableGridAdapter;
import com.google.prtutor.adapter.OrderAdapter.Callback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ExpandableGridViewActivity extends BaseActivity{

	private ExpandableListView expandableGridView;

	ExpandableGridAdapter adapter;

	private List<Map<String, Object>> list;
	private String[][] child_text_array;

	private int sign = -1;// 控制列表的展开

	private ImageButton btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_gridview);
		initViews();
		initModle();
		initEvents();
	}

	@Override
	protected void initViews() {
		expandableGridView = (ExpandableListView) findViewById(R.id.list);
		btn_back = (ImageButton) findViewById(R.id.header_setting_imagebutton);
		child_text_array = Model.EXPANDABLE_MOREGRIDVIEW_TXT;
	}

	@Override
	protected void initEvents() {

		expandableGridView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// 展开被选的group
				if (sign == -1) {
					expandableGridView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					expandableGridView.setSelectedGroup(groupPosition);
					sign = groupPosition;
				} else if (sign == groupPosition) {
					expandableGridView.collapseGroup(sign);
					sign = -1;
				} else {
					expandableGridView.collapseGroup(sign);
					expandableGridView.expandGroup(groupPosition);
					// 展开被选的group
					expandableGridView.setSelectedGroup(groupPosition);
					// 设置被选中的group置于顶端
					sign = groupPosition;
				}
				return true;
			}
		});
		expandableGridView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				showShortToast("listview的item被点击了！，点击的位置是-->");
				return false;
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish(); // 返回
			}
		});

	}

	private void initModle() {
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < Model.EXPANDABLE_GRIDVIEW_TXT.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("txt", Model.EXPANDABLE_GRIDVIEW_TXT[i]);
			list.add(map);
		}
		adapter = new ExpandableGridAdapter(this, list, child_text_array);
		expandableGridView.setAdapter(adapter);

	}
}
