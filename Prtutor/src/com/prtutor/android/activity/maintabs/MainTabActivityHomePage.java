package com.prtutor.android.activity.maintabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.prtutor.BaseDialog;
import com.google.prtutor.Next_Search;
import com.google.prtutor.R;
import com.google.prtutor.SearchActivity;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.activity.person.PersonSetting;
import com.prtutor.android.activity.teacher.TeacherAction;
import com.prtutor.android.view.AutoScrollViewPager;
import com.prtutor.android.view.ExpandableGridViewActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainTabActivityHomePage extends Fragment {
	private AutoScrollViewPager autoScrollViewPager;
	private LinearLayout layoutDot;
	private LinearLayout top_right;
	private TextView textView;
	private List<String> strings;
	private List<String> img;
	private ListView listView;
	private List<String> items = new ArrayList<>();
	private MyAdapter adapter;
	private View parentView;
	private GridView gridView;
	private LinearLayout ll_city;
	ArrayList<HashMap<String, Object>> listData; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.activity_maintabs_homepage, container, false);
		return parentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
		listView = (ListView) parentView.findViewById(R.id.lv);
		View headView = parentView.inflate(getActivity(), R.layout.head_layout, null);
		autoScrollViewPager = (AutoScrollViewPager) headView.findViewById(R.id.auto);
		textView = (TextView) headView.findViewById(R.id.tv);
		layoutDot = (LinearLayout) headView.findViewById(R.id.ll_dot);
		autoScrollViewPager.setLooping(true);
		autoScrollViewPager.setTitles(strings, textView);
		autoScrollViewPager.setImgUrl(img);
		autoScrollViewPager.setDelayTime(2000);
		autoScrollViewPager.init(4, layoutDot);
		listView.addHeaderView(headView);
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		initView();
		initEvents();
		autoScrollViewPager.startScroll();
	}

	private void initView() {
		top_right = (LinearLayout) getActivity().findViewById(R.id.top_right);
		ll_city = (LinearLayout) getActivity().findViewById(R.id.ll_city);
	}

	public void initEvents() {
		top_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
				startActivity(intent);// 启动SearchActivity

			}
		});
		ll_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new
				// Intent(getActivity().getApplicationContext(),
				// CityActivity.class);
				// startActivity(intent); // 启动城市定位
			}
		});
		autoScrollViewPager.setOnItemClickListener(new AutoScrollViewPager.OnItemClickListener() {
			@Override
			public void onItemClick(int postion) {
				Toast.makeText(getActivity().getBaseContext(), "点击了" + postion, Toast.LENGTH_SHORT).show();
				// 点击轮播画面
			}
		});
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            	String title = listData.get(pos).get("title").toString();
				Intent intent = new Intent(getActivity(), Next_Search.class);
				intent.putExtra("title", title);
				startActivity(intent);
            }
        });
	}

	private void initData() {
		strings = new ArrayList<>();
		strings.add("吉他一对一");
		strings.add("chris lee");
		strings.add("chris lee");
		strings.add("chris lee");

		img = new ArrayList<>();
		img.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489393074454&di=7008fbbb77f3bec460da39994c314c1c&imgtype=0&src=http%3A%2F%2Fwww.08160.cn%2Fuploads%2Fallimg%2F160913%2F17-160913160948.jpg");
		img.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489393074454&di=7008fbbb77f3bec460da39994c314c1c&imgtype=0&src=http%3A%2F%2Fwww.08160.cn%2Fuploads%2Fallimg%2F160913%2F17-160913160948.jpg");
		img.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489393074454&di=7008fbbb77f3bec460da39994c314c1c&imgtype=0&src=http%3A%2F%2Fwww.08160.cn%2Fuploads%2Fallimg%2F160913%2F17-160913160948.jpg");
		img.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489393074454&di=7008fbbb77f3bec460da39994c314c1c&imgtype=0&src=http%3A%2F%2Fwww.08160.cn%2Fuploads%2Fallimg%2F160913%2F17-160913160948.jpg");

		gridView = (GridView) getActivity().findViewById(R.id.class_gridView);
		List<Integer> images = new ArrayList<>();
		images.add(R.drawable.homeimg1);
		images.add(R.drawable.homeimg2);
		images.add(R.drawable.homeimg3);
		images.add(R.drawable.homeimg4);
		images.add(R.drawable.homeimg5);
		images.add(R.drawable.homeimg6);
		images.add(R.drawable.homeimg7);
		images.add(R.drawable.homeimg8);
		List<String> titleclass = new ArrayList<>();
		titleclass.add("幼小");
		titleclass.add("初中");
		titleclass.add("高中");
		titleclass.add("艺术");
		titleclass.add("生活");
		titleclass.add("语言");
		titleclass.add("职业");
		titleclass.add("其他");
		listData = new ArrayList<HashMap<String, Object>>();
		for (int j = 0; j < 8; j++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("images", images.get(j));
			map.put("title", titleclass.get(j));
			listData.add(map);
		}
		BaseAdapter adapter = new SimpleAdapter(getActivity(), listData, R.layout.item_gridview_class,
				new String[] { "images", "title" }, new int[] { R.id.item_gv_iv, R.id.item_gv_tv });
		gridView.setAdapter(adapter);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getActivity(), R.layout.itme_layout, null);
			TextView textView = (TextView) view.findViewById(R.id.tv_item);
			textView.setText(items.get(position));
			return view;
		}
	}
}