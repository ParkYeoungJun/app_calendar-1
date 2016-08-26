package com.example.younghyeon.mycalendar3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YOUNGHYEON on 2016-08-16.
 */
public class ScheduleShowActivity  extends Activity {

    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "id";
    private static final String TAG_DATE = "date";
    private static final String TAG_MEMO = "memo";

    JSONArray schedules = null;

    ArrayList<HashMap<String, String>> scheduleList;

    ListView list;

    TextView monthText;
    int curYear;
    int curMonth;
    int curDay;

    int position;
    int year;
    int month;

    ImageView imageViewList;
    ImageView imageViewPlus;
    //HashMap<String,ArrayList<ScheduleListItem>> scheduleHash;
    ArrayList outScheduleList;
    ScheduleListAdapter scheduleAdapter;
    ArrayList<ScheduleListItem> scheduleList2;

    ListAdapter adapter;

    public static final int REQUEST_CODE_SCHEDULE_UPDATE = 1020;
    public static final int REQUEST_CODE_SCHEDULE_INPUT = 1021;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_schedule_list);
        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        imageViewList = (ImageView) findViewById(R.id.backButton);

        imageViewList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        imageViewPlus = (ImageView) findViewById(R.id.plusButton);

        imageViewPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleInputActivity.class);
                intent.putExtra("year", curYear);
                intent.putExtra("month", curMonth-1);
                intent.putExtra("day", curDay);

                startActivityForResult(intent, REQUEST_CODE_SCHEDULE_INPUT);

            }
        });

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        month = intent.getExtras().getInt("month");
        year = intent.getExtras().getInt("year");

        String str_curMonth = String.format("%02d", curMonth);
        String str_curDay = String.format("%02d", curDay);

        list = (ListView) findViewById(R.id.listView);
        scheduleList = new ArrayList<HashMap<String,String>>();
        getData("http://52.78.88.182/getdata.php?date="+curYear+"-"+str_curMonth+"-"+str_curDay);     //날짜 지정해서 데이터 파싱
    }

    protected void showList(){
        try {
            Log.e("jsonerr", "1");
            JSONObject jsonObj = new JSONObject(myJSON);
            Log.e("jsonerr", "2");
            schedules = jsonObj.getJSONArray(TAG_RESULTS);
            Log.e("jsonerr", "3");
            for(int i=0;i<schedules.length();i++){
                JSONObject c = schedules.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String date = c.getString(TAG_DATE);
                String memo = c.getString(TAG_MEMO);

                HashMap<String,String> h_schedules = new HashMap<String,String>();

                h_schedules.put(TAG_ID,id);
                h_schedules.put(TAG_DATE,date);
                h_schedules.put(TAG_MEMO,memo);

                scheduleList.add(h_schedules);
            }
            Log.e("jsonerr", "4");
//            adapter = new SimpleAdapter(
            adapter = new SimpleAdapter(
                    ScheduleShowActivity.this, scheduleList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_DATE,TAG_MEMO},
                    new int[]{R.id.id, R.id.name, R.id.address}
            );
            Log.e("jsonerr", "5");

            list.setAdapter(adapter);

            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("jsonerr", "long : " + i + ", " + l);
                    final int fin_i = i;
//                    final Adapter adapter = adapterView.getAdapter();

//                    Toast.makeText(getApplicationContext(), "long"+, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder alert_confirm = new AlertDialog.Builder(ScheduleShowActivity.this);
                    alert_confirm.setMessage("이 일정을 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String str_id = scheduleList.get(fin_i).get("id");
                                    deleteData("http://52.78.88.182/deletedata.php?id=" + str_id);
                                    Log.e("jsonerr", "fin_i " + scheduleList.get(fin_i));

                                    // 메인달력 표시에도 삭제
                                    CalendarMonthAdapter.removeSchedule(year, month, position, fin_i);

                                    scheduleList.remove(fin_i);
                                    if (adapter instanceof BaseAdapter) {
                                        ((BaseAdapter)adapter).notifyDataSetChanged();
                                    } else {
                                        throw new RuntimeException("Unexpected adapter");
                                    }
                                    Log.e("jsonerr", "Yes");

                                }
                            }).setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e("jsonerr", "No");
                                    return;
                                }
                            });
                    AlertDialog alert = alert_confirm.create();
                    alert.show();

                    return true;
                }
            });


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                     Toast.makeText(getApplicationContext(), "hi"+id, Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "hi"+scheduleList.get(position).get("date"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ScheduleUpdateActivity.class);
                    intent.putExtra("id", scheduleList.get(position).get("id"));
                    intent.putExtra("date", scheduleList.get(position).get("date"));
                    intent.putExtra("memo", scheduleList.get(position).get("memo"));
                    intent.putExtra("pos", ""+position);

                    Log.e("jsonerr", "id : "+ scheduleList.get(position).get("id"));
                    Log.e("jsonerr", "date : " + scheduleList.get(position).get("date"));
                    Log.e("jsonerr", "memo"+ scheduleList.get(position).get("memo"));

                    startActivityForResult(intent, REQUEST_CODE_SCHEDULE_UPDATE);

                    if (adapter instanceof BaseAdapter) {
                        ((BaseAdapter)adapter).notifyDataSetChanged();
                    } else {
                        throw new RuntimeException("Unexpected adapter");
                    }
                }
            });


        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+myJSON);
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){
        // 뒤로가기 버튼 이벤트 처리
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        return false;
    }

    public void deleteData(String url){
        class deleteDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
//                Log.e("jsonerr", "delete" + 1);
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                    Log.e("jsonerr", "delete" + 23);
                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
//                    Log.e("jsonerr", "delete" + 4);
//                    Log.e("jsonerr", "delete" + 2);
                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result){
//                myJSON=result;
//                showList();
            }
        }
        deleteDataJSON g = new deleteDataJSON();
        g.execute(url);
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
        curMonth = it.getExtras().getInt("month")+1;

        curDay = it.getExtras().getInt("day");

        monthText.setText(curYear + "." + curMonth + "." + curDay);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_SCHEDULE_UPDATE) {
            if (intent == null) {
                return;
            }
            int pos = Integer.parseInt(intent.getStringExtra("pos"));
            String date = intent.getStringExtra("date");
            String memo = intent.getStringExtra("memo");

            Log.e("jsonerr", "pos : "+ pos);

//            scheduleList.set(pos, )
            HashMap<String,String> h_schedules = new HashMap<String,String>();
            Log.e("jsonerr", "asdfsdf");
            h_schedules.put(TAG_ID, scheduleList.get(pos).get("id"));
            h_schedules.put(TAG_DATE,date);
            h_schedules.put(TAG_MEMO,memo);

            scheduleList.set(pos, h_schedules);
            Log.e("jsonerr", "size : "+ h_schedules.toString());

            Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
                public int compare(Map<String, String> m1, Map<String, String> m2) {
                    return m1.get("date").compareTo(m2.get("date"));
                }
            };

            Collections.sort(scheduleList, mapComparator);

            if (adapter instanceof BaseAdapter) {
                ((BaseAdapter)adapter).notifyDataSetChanged();
            } else {
                throw new RuntimeException("Unexpected adapter");
            }

            //여기다가 추가시키면 됩니다.

        }
        else if (requestCode == REQUEST_CODE_SCHEDULE_INPUT) {
            if (intent == null) {
                return;
            }

            String time = intent.getStringExtra("time");
            String message = intent.getStringExtra("message");
            int selectedWeather = intent.getIntExtra("weather", 0);

            if (message != null) {
                Toast toast = Toast.makeText(getBaseContext(), "time : " + time + ", message : " + message + ", selectedWeather : " + selectedWeather, Toast.LENGTH_LONG);
                toast.show();
                // 일정 추가 저장시 토스트 메시지 띄우는 거

//                ScheduleListItem aItem = new ScheduleListItem(time, message);

//                if (outScheduleList == null) {
//                    outScheduleList = new ArrayList();
//                }
//                outScheduleList.add(aItem);

//                scheduleAdapter.scheduleList = outScheduleList;
//                scheduleAdapter.notifyDataSetChanged();

                String str_curMonth = String.format("%02d", curMonth);
                String str_curDay = String.format("%02d", curDay);

                getMaxId("http://52.78.88.182/getMaxId.php");
//                getData("http://52.78.88.182/getdata.php?date="+curYear+"-"+str_curMonth+"-"+str_curDay);



//                HashMap<String,String> h_schedules = new HashMap<String,String>();
//                Log.e("jsonerr", "asdfsdf");
////                h_schedules.put(TAG_ID, scheduleList.get(pos).get("id"));
////                h_schedules.put(TAG_DATE,date);
////                h_schedules.put(TAG_MEMO,memo);
//
////                scheduleList.set(pos, h_schedules);
//                Log.e("jsonerr", "size : "+ h_schedules.toString());
//
//                Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
//                    public int compare(Map<String, String> m1, Map<String, String> m2) {
//                        return m1.get("date").compareTo(m2.get("date"));
//                    }
//                };
//
//                Collections.sort(scheduleList, mapComparator);


            }
        }
    }

    public void getMaxId(String url){
        class getMaxIdJSON extends AsyncTask<String, Void, String> {

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
                myJSON = result;
                try {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    schedules = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<schedules.length();i++){
                        JSONObject c = schedules.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String date = c.getString(TAG_DATE);
                        String memo = c.getString(TAG_MEMO);

                        HashMap<String,String> h_schedules = new HashMap<String,String>();

                        h_schedules.put(TAG_ID,id);
                        h_schedules.put(TAG_DATE,date);
                        h_schedules.put(TAG_MEMO,memo);

                        scheduleList.add(h_schedules);
                    }
                    Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
                        public int compare(Map<String, String> m1, Map<String, String> m2) {
                            return m1.get("date").compareTo(m2.get("date"));
                        }
                    };

                    Collections.sort(scheduleList, mapComparator);

                    if (adapter instanceof BaseAdapter) {
                        ((BaseAdapter)adapter).notifyDataSetChanged();
                    } else {
                        throw new RuntimeException("Unexpected adapter");
                    }

                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+myJSON);
                    e.printStackTrace();
                }


//                showList();
            }
        }
        getMaxIdJSON g = new getMaxIdJSON();
        g.execute(url);
    }

}