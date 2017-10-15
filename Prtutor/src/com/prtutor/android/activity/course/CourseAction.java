package com.prtutor.android.activity.course;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.adapter.CourseAdapter;
import com.google.prtutor.dao.CommentDAO;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.dao.FavoriteDAO;
import com.google.prtutor.model.Tb_course;
import com.google.prtutor.model.Tb_favoriate;
import com.prtutor.android.activity.order.AddOrder;
import com.prtutor.android.activity.teacher.TeacherAction;
import com.prtutor.android.map.MapRoutePlan;
import com.prtutor.android.util.Const;
import com.prtutor.android.util.ServerUtils;
import com.prtutor.android.view.HandyTextView;
import com.prtutor.android.view.RoundImageview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

public class CourseAction extends BaseActivity implements OnClickListener {

	private static final String mProfile = null;
	private ViewPager viewPager;
	private ImageView imageView, id_course_img;
	private List<View> lists = new ArrayList<View>();
	private CourseAdapter crAdapter;
	private Bitmap cursor;
	private int offSet;
	private int currentItem;
	private Matrix matrix = new Matrix();
	private int bmWidth;
	private Animation animation;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView tv_nums;
	private ImageButton courseback;
	private HandyTextView coursetype;
	private HandyTextView coursename;
	private HandyTextView courseintro;
	private HandyTextView coursetime;
	private HandyTextView coursedate;
	private HandyTextView coursescore;
	private HandyTextView tv_tutor_Name, tv_tutor_desc, tv_tutor_location;
	private RoundImageview rv_tutor_icon;
	private ImageButton tab_collect_img, tab_phone_img;
	private HandyTextView tv_course_collect;
	private Button tab_buy_img;
	private ListView listView;
	private LinearLayout cr_location;

	private Cursor cursorData;
	private static int fari_flag;
	private FavoriteDAO ftDAO;
	private Tb_course course;
	private Tb_favoriate tb_favoriate;
	private CourseDAO courseDAO;
	private String type, courseName, course_time, desc, course_date, locname;
	private String trPhone, trDesc, trName;
	private int imgUrl, trId, trImg, trenrollment, container, total, betday;
	private int destLong, destLat;
	private float price, estimate;
	private CommentDAO commentDAO;
	private Intent intent;
	private ImageButton img_teacher;
	// 存储数据的数组列表
	private ArrayList<HashMap<String, Object>> listData;
	private BaseAdapter adapter;

	// 决定变量courseId、userId
	private int courseId;
	private int userId = 1;
	FinalBitmap fb = null;
	Bitmap bmp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);
		courseDAO = new CourseDAO(this);
		ftDAO = new FavoriteDAO(this);
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		courseId = bundle.getInt("courseId");
		cursorData = courseDAO.queryCourseId(courseId);
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		String username = sp.getString("username", "");
		tb_favoriate = new Tb_favoriate(courseId, username);
		commentDAO = new CommentDAO(this);
		intent = new Intent();
		fb = FinalBitmap.create(this);
		initViews();
		queryData();
		initEvents();
		if (ftDAO.hasFavorite(tb_favoriate)) {
			selectImg(0);
		} else {
			selectImg(1);
		}
		crAdapter = new CourseAdapter(lists);
		initviewPager();
		viewPager.setAdapter(crAdapter);
		viewPager.setCurrentItem(0);
	}

	@Override
	protected void initViews() {

		imageView = (ImageView) findViewById(R.id.cursor);
		id_course_img = (ImageView) findViewById(R.id.id_course_img);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		tv_nums = (TextView) findViewById(R.id.tab_buy_tv_nums);
		courseback = (ImageButton) findViewById(R.id.course_back);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		lists.add(getLayoutInflater().inflate(R.layout.course_introduce, null));
		lists.add(getLayoutInflater().inflate(R.layout.course_arrange, null));
		lists.add(getLayoutInflater().inflate(R.layout.course_estimate, null));
		coursename = (HandyTextView) findViewById(R.id.course_name);
		tab_collect_img = (ImageButton) findViewById(R.id.tab_collect_img);
		tab_phone_img = (ImageButton) findViewById(R.id.tab_phone_img);
		tv_course_collect = (HandyTextView) findViewById(R.id.tv_course_collect);
		tab_buy_img = (Button) findViewById(R.id.tab_buy_img);
	}

	@Override
	protected void initEvents() {
		textView1.setOnClickListener(this);
		textView2.setOnClickListener(this);
		textView3.setOnClickListener(this);
		courseback.setOnClickListener(this);
		tab_collect_img.setOnClickListener(this);
		tab_buy_img.setOnClickListener(this);
		if (container < trenrollment) {
			tab_phone_img.setOnClickListener(this);
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) { //
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					textView1.setBackgroundColor(getResources().getColor(R.color.orange));
					if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(offSet * 4 + 2 * bmWidth, 0, 0, 0);
					}
					break;
				case 1:
					textView2.setBackgroundColor(getResources().getColor(R.color.orange));
					coursetype = (HandyTextView) findViewById(R.id.cr_type);
					coursetime = (HandyTextView) findViewById(R.id.cr_time);
					coursedate = (HandyTextView) findViewById(R.id.cr_date);
					cr_location = (LinearLayout) findViewById(R.id.cr_location);
					tv_tutor_location = (HandyTextView) findViewById(R.id.cr_hv_location);
					coursetype.setText(type + "/隔" + betday + "天(共" + total + "节课)");
					tv_tutor_location.setText(locname);
					StringBuffer str = new StringBuffer();
					str.append(course_time);
					str.insert(str.length() / 2, "至");
					coursetime.setText("" + str);
					coursedate.setText(course_date);
					cr_location.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							intent.setClass(CourseAction.this, MapRoutePlan.class);
							intent.putExtra("destLong", destLong);
							intent.putExtra("destLat", destLat);
							startActivity(intent);
						}

					});
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
					} else if (currentItem == 2) {
						// TODO
						animation = new TranslateAnimation(2 * offSet + 2 * bmWidth, offSet * 2 + bmWidth, 0, 0);
					}
					break;
				case 2:
					textView3.setBackgroundColor(getResources().getColor(R.color.orange));
					listView = (ListView) findViewById(R.id.pl_list_lv);
					queryComment();
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, 4 * offSet + 2 * bmWidth, 0, 0);
					} else if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth, 0, 0);
					}
				}
				currentItem = arg0;
				animation.setDuration(100);
				animation.setFillAfter(true);
				imageView.startAnimation(animation);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void initviewPager() {
		textView1.setBackgroundColor(getResources().getColor(R.color.orange));
		tv_tutor_Name = (HandyTextView) lists.get(0).findViewById(R.id.tv_tutor_Name);
		tv_tutor_desc = (HandyTextView) lists.get(0).findViewById(R.id.tv_tutor_desc);
		rv_tutor_icon = (RoundImageview) lists.get(0).findViewById(R.id.course_rv_tutor_icon);
		courseintro = (HandyTextView) lists.get(0).findViewById(R.id.cr_desc);
		coursescore = (HandyTextView) lists.get(0).findViewById(R.id.cr_score);
		courseintro.setText("    " + desc);
		tv_tutor_Name.setText(trName);
		if (trDesc == null) {
			trDesc = "无";
		}
		tv_tutor_desc.setText(trDesc);
		coursescore.setText("￥" + price + "  (评分:" + estimate + ")");
		rv_tutor_icon.setImageResource(trImg);
		rv_tutor_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				intent.setClass(CourseAction.this, TeacherAction.class);
				intent.putExtra("trId", trId);
				startActivity(intent);
			}

		});
	}

	@Override
	public void onClick(View arg0) {
		resetImgs();
		switch (arg0.getId()) {
		case R.id.textView1:
			viewPager.setCurrentItem(0);
			break;
		case R.id.textView2:
			viewPager.setCurrentItem(1);
			break;
		case R.id.textView3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.course_back:
			defaultFinish();
			break;
		case R.id.tab_collect_img:
			if (fari_flag == 0) {
				setSelect(0);
			} else {
				setSelect(1);
			}
			break;
		case R.id.tab_buy_img:
			intent.setClass(this, AddOrder.class);
			intent.putExtra("courseId", courseId);
			intent.putExtra("stuname", userId);
			intent.putExtra("courseName", courseName);
			intent.putExtra("price", price);
			startActivity(intent);
			break;
		case R.id.tab_phone_img:
			String tel = "tel:" + trPhone;
			intent.setAction(Intent.ACTION_CALL);
			intent.setData(Uri.parse(tel));
			startActivity(intent);
			break;
		}
	}

	private void setSelect(int i) {
		switch (i) {
		case 0:
			selectImg(0);
			ftDAO.addFavorite(tb_favoriate);
			break;
		case 1:
			selectImg(1);
			ftDAO.deleteFavorite(tb_favoriate);
			break;
		}
	}

	private void selectImg(int i) {
		switch (i) {
		case 0:
			tab_collect_img.setImageResource(R.drawable.tab_favorite_selected);
			tv_course_collect.setText("已收藏");
			fari_flag = 1;
			break;
		case 1:
			tab_collect_img.setImageResource(R.drawable.tab_favorite_normal);
			tv_course_collect.setText("收藏");
			fari_flag = 0;
			break;
		}
	}

	private void initeCursor(String img) {
		cursor = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		bmWidth = cursor.getWidth();

		DisplayMetrics dm;
		dm = getResources().getDisplayMetrics();

		offSet = (dm.widthPixels - 3 * bmWidth) / 6;
		matrix.setTranslate(offSet, 0);
		imageView.setImageMatrix(matrix); //
		currentItem = 0;
	}

	/**
	 * 课程信息初始化
	 */
	private void queryData() {

		if (cursorData.moveToFirst()) {
			type = cursorData.getString(1);
			courseName = cursorData.getString(2);
			course_date = cursorData.getString(3);
			course_time = cursorData.getString(4);
			price = cursorData.getFloat(5);
			container = cursorData.getInt(6);
			desc = cursorData.getString(7);
			imgUrl = cursorData.getInt(8);
			destLong = cursorData.getInt(9);
			destLat = cursorData.getInt(10);
			trId = cursorData.getInt(11);
			trName = cursorData.getString(12);
			trImg = cursorData.getInt(13);
			trPhone = cursorData.getString(14);
			trDesc = cursorData.getString(15);
			estimate = cursorData.getFloat(16);
			trenrollment = cursorData.getInt(17);
			locname = cursorData.getString(18);
			total = cursorData.getInt(19);
			betday = cursorData.getInt(20);
			tv_nums.setText(trenrollment + "人报名");
			coursename.setText(courseName);
			id_course_img.setImageResource(imgUrl);
		}
	}

	/**
	 * 评论信息初始化
	 */
	private void queryComment() {

		Cursor cursor = commentDAO.queryComment(courseId);
		int columnsSize = cursor.getColumnCount();
		int j = 1;
		listData = new ArrayList<HashMap<String, Object>>();//怎么会有六条
		while (cursor.moveToNext()) {
			HashMap<String, Object> mapcomment = new HashMap<String, Object>();
			mapcomment.put("floor", "#" + columnsSize);
			for (int i = 0; i < columnsSize; i++) {
				mapcomment.put("username", cursor.getString(0));
				mapcomment.put("createTime", cursor.getString(1));
				mapcomment.put("comment", cursor.getString(2));
			}
			listData.add(mapcomment);
			j++;
		}
		// 创建adapter适配器对象
		adapter = new SimpleAdapter(this, listData, R.layout.item_list_comment,
				new String[] { "username", "createTime", "comment", "floor" }, new int[] { R.id.list_comment_name,
						R.id.list_comment_time, R.id.list_comment_pl, R.id.list_comment_floor });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	// 切换字至暗色
	private void resetImgs() {
		textView1.setBackgroundColor(R.drawable.course_item_bg);
		textView2.setBackgroundColor(R.drawable.course_item_bg);
		textView3.setBackgroundColor(R.drawable.course_item_bg);
	}
}
