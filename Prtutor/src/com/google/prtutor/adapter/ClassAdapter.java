package com.google.prtutor.adapter;

import com.google.prtutor.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ClassAdapter extends BaseAdapter {
	
	private Context context;
    private Integer[] images  = {
    		R.drawable.homeimg1,
    		R.drawable.homeimg2,
    		R.drawable.homeimg3,
    		R.drawable.homeimg4,
    		R.drawable.homeimg5,
    		R.drawable.homeimg6,
    		R.drawable.homeimg7,
    		R.drawable.homeimg8,
    };
    private String[] texts = {
    		"幼小",
    		"初中",
    		"高中",
    		"艺术",
    		"生活",
    		"语言",
    		"职业",
    		"更多"
    };
    
	public ClassAdapter(Context context){
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
