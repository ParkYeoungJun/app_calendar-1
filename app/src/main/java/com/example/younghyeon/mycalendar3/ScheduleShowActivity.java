package com.example.younghyeon.mycalendar3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YOUNGHYEON on 2016-08-16.
 */
public class ScheduleShowActivity  extends Activity {

    TextView scheduleText;
    TextView monthText;
    int curYear;
    int curMonth;
    int curDay;

    ListView scheduleList;
    ArrayList<ScheduleListItem> outScheduleList;
    HashMap<String,ArrayList<ScheduleListItem>> scheduleHash;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_schedule_list);
        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();
        // 제목에 년월일 설정
/*
        scheduleHash = new HashMap<String,ArrayList<ScheduleListItem>>();
        outScheduleList = getSchedule(curDay);
        scheduleText = (TextView) findViewById(R.id.scheduleText);
        scheduleText.setText(outScheduleList.size());
        여기가 잘못된 것 같다
*/
    }

    private void setMonthText() {
        Intent it = getIntent();
        curYear = it.getExtras().getInt("year");
        curMonth = it.getExtras().getInt("month");
        curDay = it.getExtras().getInt("day");
        monthText.setText(curYear + "." + (curMonth + 1) + "." + curDay);
    }

    public ArrayList<ScheduleListItem> getSchedule(int position) {
        String keyStr = curYear + "-" + curMonth + "-" + position;
        ArrayList<ScheduleListItem> outList = scheduleHash.get(keyStr);
        return outList;
    }
}