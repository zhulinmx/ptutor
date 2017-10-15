package com.google.prtutor.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.prtutor.R;
import com.google.prtutor.SearchActivity;
import com.prtutor.android.activity.maintabs.MainTabActivityPerson;
import com.google.prtutor.MyGridView;

public class ExpandableGridAdapter extends BaseExpandableListAdapter{

	private String[][] child_text_array;
	private Context context;
	private MyGridView gridview;

	private List<Map<String, Object>> list;
	List<String> child_array;

	public ExpandableGridAdapter(Context context,
			List<Map<String, Object>> list, String[][] child_text_array) {
		this.context = context;
		this.list = list;
		this.child_text_array = child_text_array;
	}

	//��ȡһ����ǩ����
	@Override
	public int getGroupCount() {
		return list.size();
	}
    //��ȡһ����ǩ�¶�����ǩ������
	@Override
	public int getChildrenCount(int groupPosition) {
	  // ���ﷵ��1��Ϊ����ExpandableListViewֻ��ʾһ��ChildView��������չ��
	  // ExpandableListViewʱ����ʾ��ChildCount������ͬ��GridView
		return 1;
	}

	//��ȡһ����ǩ����
	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition).get("txt");
	}

	//��ȡһ����ǩ�¶�����ǩ������
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child_text_array[groupPosition][childPosition];
	}

	//��ȡһ����ǩ��ID
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	//��ȡ������ǩ��ID
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	//ָ��λ����Ӧ������ͼ
	@Override
	public boolean hasStableIds() {
		return true;
	}

	//��һ����ǩ��������
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = (LinearLayout) LinearLayout.inflate(context,
				R.layout.item_gridview_group_layout, null);

		TextView group_title = (TextView) convertView
				.findViewById(R.id.group_title);
		//�ж��Ƿ�չ��
		if (isExpanded) {
			group_title.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_down, 0);
			group_title.setBackgroundColor(group_title.getResources().getColor(R.color.light_gray));
		} else {
			group_title.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_up, 0);
			group_title.setBackgroundColor(group_title.getResources().getColor(R.color.white));
		}
		group_title.setText(list.get(groupPosition).get("txt").toString());
		//Toast.makeText(context, "setText:" + list.get(groupPosition).get("txt").toString(),
		//		Toast.LENGTH_SHORT).show();
		return convertView;
	}

	//��һ����ǩ�µĶ�����ǩ��������
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView = (RelativeLayout) RelativeLayout.inflate(context,
				R.layout.item_grid_child_layout, null);
		gridview = (MyGridView) convertView.findViewById(R.id.gridview);

		int size = child_text_array[groupPosition].length;
		child_array = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			child_array.add(child_text_array[groupPosition][i]);
		}
		gridview.setAdapter(new GridTextAdapter(context, child_array));
//    	gridview.setOnItemClickListener(this);
		return convertView;
	}

	// ��ѡ���ӽڵ��ʱ�򣬵��ø÷���
	// true�������ѡ��false�������ѡ
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}	
	
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//			
//	}
	

}