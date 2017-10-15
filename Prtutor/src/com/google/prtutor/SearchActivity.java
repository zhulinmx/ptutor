package com.google.prtutor;

import java.util.Date;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.CourseDAO;
import com.google.prtutor.dao.RecordDAO;
import com.prtutor.android.dialog.EditTextDialog;
import com.prtutor.android.view.SearchListView;
import com.google.prtutor.BaseDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private BaseDialog testdlg;
	private EditTextDialog editdlg;
	private Context mContext;
	private EditText et_search;
	private TextView tv_tip;
	private SearchListView listView;
	private RecordDAO recordDAO;
	private CourseDAO courseDAO;
	private TextView tv_clear;
	private BaseAdapter adapter;
	private ImageButton back_search;  
	private Cursor cursor;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		recordDAO = new RecordDAO(SearchActivity.this);
		courseDAO = new CourseDAO(SearchActivity.this); 
		initViews();
		initEvents();
		// ������ļ�������������ص�
		et_search.setOnKeyListener(new View.OnKeyListener() {// ������󰴼����ϵ�������

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// �޸Ļس�������
					// �����ؼ���
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					// �����������󽫵�ǰ��ѯ�Ĺؼ��ֱ�������,����ùؼ����Ѿ����ھͲ�ִ�б���
					boolean hasData = recordDAO.hasRecord(et_search.getText().toString().trim());		
					if (!hasData) {
						recordDAO.insertData(et_search.getText().toString().trim());
						queryData("");			
					}
					showShortToast(et_search.getText().toString());
					Intent intent = new Intent(SearchActivity.this, Next_Search.class);
					intent.putExtra("search", et_search.getText().toString());
					startActivity(intent);

				}
				return false;
			}
		});
		// ��������ı��仯ʵʱ����
		et_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().trim().length() == 0) {
					tv_tip.setText("������ʷ");
				} else {
					tv_tip.setText("�������");
				}
				String tempName = et_search.getText().toString();
				// ����tempNameȥģ����ѯ���ݿ�����û������
				queryData(tempName);
			}
		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView textView = (TextView) view.findViewById(android.R.id.text1);
				String txt_history = textView.getText().toString();
				Intent intent = new Intent(SearchActivity.this, Next_Search.class);
				intent.putExtra("search", txt_history);
				startActivity(intent);
			}
		});
		//��ʼʱ��ȡȫ��������ʷ
		queryData("");
	}

	@Override
	protected void initViews() {
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		et_search = (EditText) findViewById(R.id.et_search);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		listView = (com.prtutor.android.view.SearchListView) findViewById(R.id.listView);
		back_search = (ImageButton) findViewById(R.id.back_search);
		
		// ����EditText��ߵ�������ť�Ĵ�С
		Drawable drawable = getResources().getDrawable(R.drawable.icon_search);
		drawable.setBounds(0, 0, 60, 60);// ��һ0�Ǿ���߾��룬�ڶ�0�Ǿ��ϱ߾��룬60�ֱ��ǳ���
		et_search.setCompoundDrawables(drawable, null, null, null);// ֻ�����
	}

	@Override
	protected void initEvents() {
		tv_clear.setOnClickListener(this);
		back_search.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.tv_clear:
			recordDAO.deleteRecord();
			queryData("");
			break;
		
		case R.id.back_search:
			defaultFinish();
			break;

		}
	}
	/**
	 * ģ����ѯ����
	 */
	private void queryData(String tempName) {
		cursor = recordDAO.queryRecord(tempName);
		// ����adapter����������
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "words" },
				new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		// ����������
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

}
