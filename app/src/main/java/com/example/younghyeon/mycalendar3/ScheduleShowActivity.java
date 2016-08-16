package com.example.younghyeon.mycalendar3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YOUNGHYEON on 2016-08-16.
 */
public class ScheduleShowActivity  extends Activity {

    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_DATE = "date";
    private static final String TAG_MEMO = "memo";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;


//    TextView scheduleText;
    TextView monthText;
    int curYear;
    int curMonth;
    int curDay;
//
//    ListView scheduleList;
//    ScheduleListAdapter scheduleAdapter;
//    ArrayList<ScheduleListItem> outScheduleList;
//    HashMap<String,ArrayList<ScheduleListItem>> scheduleHash;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_schedule_list);

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        String str_curMonth = String.format("%02d", curMonth);
        String str_curDay = String.format("%02d", curDay);

        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();

        getData("http://52.78.88.182/getdata.php?date="+curYear+"-"+str_curMonth+"-"+str_curDay);     //날짜 지정해서 데이터 파싱
//        getData("http://52.78.88.182/getdata.php");

//        scheduleList = (ListView)findViewById(R.id.scheduleList);
//        scheduleAdapter = new ScheduleListAdapter(this);
//        scheduleList.setAdapter(scheduleAdapter);
//

        // 제목에 년월일 설정
/*
        scheduleHash = new HashMap<String,ArrayList<ScheduleListItem>>();
        outScheduleList = getSchedule(curDay);
        scheduleText = (TextView) findViewById(R.id.scheduleText);
        scheduleText.setText(outScheduleList.size());
        여기가 잘못된 것 같다
*/
    }

    protected void showList(){
        try {
            Log.e("jsonerr", "1");
            JSONObject jsonObj = new JSONObject(myJSON);
            Log.e("jsonerr", "2");
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            Log.e("jsonerr", "3");
            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String date = c.getString(TAG_DATE);
                String memo = c.getString(TAG_MEMO);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_DATE,date);
                persons.put(TAG_MEMO,memo);

                personList.add(persons);
            }
            Log.e("jsonerr", "4");
            ListAdapter adapter = new SimpleAdapter(
                    ScheduleShowActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_DATE,TAG_MEMO},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );
            Log.e("jsonerr", "5");
            list.setAdapter(adapter);

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+myJSON);
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }



    private void setMonthText() {
        Intent it = getIntent();
        curYear = it.getExtras().getInt("year");
        curMonth = it.getExtras().getInt("month");

        // curMonth + 1 해서 넘어 옴
        curDay = it.getExtras().getInt("day");

        monthText.setText(curYear + "." + curMonth + "." + curDay);
    }
//
//    public ArrayList<ScheduleListItem> getSchedule(int position) {
//        String keyStr = curYear + "-" + (curMonth - 1)+ "-" + position;
//        ArrayList<ScheduleListItem> outList = scheduleHash.get(keyStr);
//
//        return outList;
//    }
}