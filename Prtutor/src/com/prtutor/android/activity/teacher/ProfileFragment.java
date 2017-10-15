package com.prtutor.android.activity.teacher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.prtutor.R;
import com.google.prtutor.dao.TeacherDAO;
import com.prtutor.android.activity.course.CourseAction;
import com.prtutor.android.view.HandyTextView;
import com.prtutor.android.view.RoundImageview;

public class ProfileFragment extends Fragment {
	private HandyTextView etdesc, etquotation, etname, etsex, etage;
	private LinearLayout tutor_desc, tutor_qua, tutor_pro;
	private RoundImageview rv_tutor_icon;
	private Intent intent;
	private TeacherDAO teacherDAO;
	String desc, quotation;
	int img;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.teacher_profile, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		intent = new Intent();
		teacherDAO = new TeacherDAO(getActivity());
		initView();
		initTutor();
		initEvents();
	}

	private void initView() {
		tutor_desc = (LinearLayout) getActivity().findViewById(R.id.tutor_desc);
		tutor_qua = (LinearLayout) getActivity().findViewById(R.id.tutor_quotation);
		tutor_pro = (LinearLayout) getActivity().findViewById(R.id.tutor_pro);
		etdesc = (HandyTextView) getActivity().findViewById(R.id.et_profile_desc);
		etquotation = (HandyTextView) getActivity().findViewById(R.id.et_profile_quotation);
		etname = (HandyTextView) getActivity().findViewById(R.id.hv_tutor_name);
		etsex = (HandyTextView) getActivity().findViewById(R.id.hv_tutor_sex);
		etage = (HandyTextView) getActivity().findViewById(R.id.hv_tutor_age);
		rv_tutor_icon = (RoundImageview) getActivity().findViewById(R.id.pr_rv_tutor_icon);
	}

	public void initEvents() {
		tutor_desc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent.setClass(getActivity(), TeacherEdit.class);
				intent.putExtra("editfalg", 0);
				intent.putExtra("editcontext", desc);
				startActivity(intent);
			}
		});
		tutor_qua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent.setClass(getActivity(), TeacherEdit.class);
				intent.putExtra("editfalg", 1);
				intent.putExtra("editcontext", quotation);
				startActivity(intent);
			}
		});
		tutor_pro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent.setClass(getActivity(), TeacherSetting.class);
				startActivity(intent);
			}
		});
	}

	public void initTutor() {
		SharedPreferences sp = getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
		String teachname = sp.getString("teachname", "");
		intent.putExtra("teachname", teachname);
		Cursor cursorData = teacherDAO.queryTeacherName(teachname.toString().trim());
		if (cursorData.moveToFirst()) {
			desc = cursorData.getString(1);
			img = cursorData.getInt(0);
			quotation = cursorData.getString(4);
			if (cursorData.getString(6) == null) {
				etsex.setText("Œﬁ");
			}else{
				etsex.setText(cursorData.getString(6));
			}
			if (cursorData.getString(8) == null) {
				etname.setText(teachname.toString().trim());
			}else{
				etname.setText(cursorData.getString(8));
			}
			rv_tutor_icon.setBackgroundResource(img);
			etage.setText("ΩÃ¡‰£∫" + cursorData.getInt(5) + "ƒÍ");
			etdesc.setText(desc);
			etquotation.setText(quotation);
		}
	}
}
