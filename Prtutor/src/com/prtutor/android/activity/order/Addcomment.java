package com.prtutor.android.activity.order;

import com.google.prtutor.BaseActivity;
import com.google.prtutor.R;
import com.google.prtutor.dao.CommentDAO;
import com.google.prtutor.dao.OrderDAO;
import com.google.prtutor.model.Tb_comment;
import com.prtutor.android.view.StarBarView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Addcomment extends BaseActivity implements OnClickListener {

	private ImageButton comment_back;
	private EditText et_comment;
	private StarBarView mStarbar;
	private Button btn_ok;
	private String coursename, stuName;
	int courseid, oderid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_comment);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		coursename = bundle.getString("coursename");
		stuName = bundle.getString("stuName");
		courseid = bundle.getInt("courseid");
		oderid = bundle.getInt("oderid");
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		comment_back = (ImageButton) findViewById(R.id.comment_back);
		et_comment = (EditText) findViewById(R.id.et_comment);
		btn_ok = (Button) findViewById(R.id.comment_finish);
		mStarbar = (StarBarView) findViewById(R.id.sbv_starbar);
	}

	@Override
	protected void initEvents() {
		comment_back.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.comment_back:
			defaultFinish();
			break;

		case R.id.comment_finish:
			Time time = new Time();
			time.setToNow();
			String currentTime = time.format("%y-%m-%d %H:%M:%S");
			CommentDAO commentDAO = new CommentDAO(this);
			OrderDAO orderDAO = new OrderDAO(this);
			String comment = et_comment.getText().toString();
			// 拿到当前星星数量
			float starRating = mStarbar.getStarRating();
			Tb_comment tb_comment = new Tb_comment(comment, currentTime, stuName, courseid);
			//更新订单状态、添加评论
			orderDAO.updateOdstage(oderid,starRating,courseid);
			commentDAO.AddComment(tb_comment);
			showShortToast("starRating" + starRating);
			showShortToast("谢谢您的评价~" + tb_comment.getCreateTime() + tb_comment.getStuName() + tb_comment.getComment());
			defaultFinish();
			break;
		}
	}
}
