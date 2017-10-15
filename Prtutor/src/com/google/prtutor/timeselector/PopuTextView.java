package com.google.prtutor.timeselector;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Printer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.prtutor.R;

public class PopuTextView extends RelativeLayout{
    private View contentView;
    private TimeSelectorView timeSelectorView;
    private PopupWindow mPopupWindow;
    private String mDefault = "选择日期";
    private TextView mTextView;
    private ImageView mImageView;
    private TimeSelectorView.TimeChangeListener mListener;
    private Drawable mDrawable;
    private int mTextColorUnChecked = 0xff666666;
    private int mTextColorChecked = 0xff378ad3;

    public PopuTextView(Context context) {
        this(context, null);
    }

    public PopuTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopuTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopuTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) 
    {
        super(context, attrs, defStyleAttr);
        float density = context.getResources().getDisplayMetrics().density;

        mTextView = new TextView(context);
        mTextView.setId(R.id.mTextViewId);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mTextView.setLayoutParams(layoutParams);
        mTextView.setTextColor(getResources().getColor(R.color.blue));
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        });

        if(mTextView.getText() != null && !mTextView.getText().equals("")) {
            mDefault = mTextView.getText().toString();
        }
        mTextView.setText(mDefault);
        addView(mTextView);

        mDrawable = context.getResources().getDrawable(R.drawable.cancle);

        mImageView = new ImageView(context);
        layoutParams = new LayoutParams((int)(density*12), (int)(density*12));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setImageDrawable(mDrawable);
        mImageView.setVisibility(INVISIBLE);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(mDefault);
                mTextView.setTextColor(mTextColorUnChecked);
                v.setVisibility(INVISIBLE);
            }
        });
        addView(mImageView);

        contentView = LayoutInflater.from(context).inflate(R.layout.popu_timeselector, null);


        timeSelectorView = (TimeSelectorView) contentView.findViewById(R.id.timeselector);
//      timeSelectorView = new TimeSelectorView(context, attrs, defStyleAttr);

        timeSelectorView.setListener(initTimeChangeListener());

        /**opupWindow*/
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);

    }

    private TimeSelectorView.TimeChangeListener initTimeChangeListener() {
        mListener = new TimeSelectorView.TimeChangeListener() {

            @Override
            public void scrollFinish(String time) {
                mTextView.setText(time);  
                TimePlay(time);
                mImageView.setVisibility(VISIBLE);
                mTextView.setTextColor(mTextColorChecked);
//              mImageView.setImageDrawable(mDrawable);
            }

            @Override
            public void onFinish() {
                mPopupWindow.dismiss();
            }

            @Override
            public void onCancle() {
                mTextView.setText(mDefault);
                mTextView.setTextColor(mTextColorUnChecked);
                mImageView.setVisibility(INVISIBLE);
                mPopupWindow.dismiss();
            }
        };
        return mListener;
    }
   
    private String TimePlay(String time){
    	return time;
    }
}
