package com.google.prtutor;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.prtutor.android.view.CalendarView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextSelectMonth;
    private ImageButton mLastMonthView;
    private ImageButton mNextMonthView;
    private CalendarView mCalendarView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSelectMonth = (TextView) findViewById(R.id.txt_select_month);
        mLastMonthView = (ImageButton) findViewById(R.id.img_select_last_month);
        mNextMonthView = (ImageButton) findViewById(R.id.img_select_next_month);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mLastMonthView.setOnClickListener(this);
        mNextMonthView.setOnClickListener(this);

        // ��ʼ����ѡ����
        initData();

        // ���ÿ�ѡ����
        mCalendarView.setOptionalDate(mDatas);
        // ������ѡ����
//        mCalendarView.setSelectedDates(mDatas);
        // ���ò����Ա����
//        mCalendarView.setClickable(false);

        // ���õ���¼�
        mCalendarView.setOnClickDate(new CalendarView.OnClickListener() {
            @Override
            public void onClickDateListener(int year, int month, int day) {
                Toast.makeText(getApplication(), year + "��" + month + "��" + day + "��", Toast.LENGTH_SHORT).show();

                // ��ȡ��ѡ������
                List<String> dates = mCalendarView.getSelectedDates();
                for (String date : dates) {
                    Log.e("test", "date: " + date);
                }
            }
        });

        mTextSelectMonth.setText(mCalendarView.getDate());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("20170416");
        mDatas.add("20170417");
        mDatas.add("20170420");
        mDatas.add("20170421");
        mDatas.add("20170427");
        mDatas.add("20170429");
        mDatas.add("20170512");
        mDatas.add("20170516");
        mDatas.add("20170519");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_select_last_month:
                mCalendarView.setLastMonth();
                mTextSelectMonth.setText(mCalendarView.getDate());
                break;
            case R.id.img_select_next_month:
                mCalendarView.setNextMonth();
                mTextSelectMonth.setText(mCalendarView.getDate());
                break;
        }
    }
}
