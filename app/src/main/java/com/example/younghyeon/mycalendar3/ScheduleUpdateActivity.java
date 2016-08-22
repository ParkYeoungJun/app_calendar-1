package com.example.younghyeon.mycalendar3;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleUpdateActivity extends Activity {
    public static final String TAG = "ScheduleUpdateActivity";

    EditText messageInput;
    Button timeButton;

    int curYear, curMonth, curDay;
    int selectedHour, selectedMin;
    String sqlTimeStr = "";
    String memo = "";

    public static final int DIALOG_TIME = 1101;

    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH시 mm분");

    Date selectedDate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_update);

        setTitle("일정 추가");

        Intent it = getIntent();
        curYear = it.getExtras().getInt("year");
        curMonth = it.getExtras().getInt("month")+1;
        curDay = it.getExtras().getInt("day");

        messageInput = (EditText) findViewById(R.id.u_messageInput);

        timeButton = (Button) findViewById(R.id.u_timeButton);
        timeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
            }
        });

        Button saveButton = (Button) findViewById(R.id.u_saveButton);
        saveButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String messageStr = messageInput.getText().toString();
                if(messageStr.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "메모를 입력하세요", Toast.LENGTH_SHORT).show();
                    return ;
                }

                String timeStr = timeButton.getText().toString();

                sqlTimeStr = curYear + "-" + String.format("%02d", curMonth) + "-" + String.format("%02d", curDay);
                sqlTimeStr += " " +  String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMin) + ":00";
                memo = messageStr;
                Log.e("jsonerr", "sqlTimeStr : "+sqlTimeStr);

                updateData("http://52.78.88.182/updatedata.php");

                Log.e("jsonerr", "postData");


                Intent intent = new Intent();
                intent.putExtra("time", timeStr);
                intent.putExtra("message", messageStr);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Button closeButton = (Button) findViewById(R.id.u_closeButton);
        closeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
        setSelectedDate(curDate);

        // process the passed intent
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_TIME:
                String timeStr = timeButton.getText().toString();
                Log.e("jsonerr", "timeStr : "+timeStr);

                Calendar calendar = Calendar.getInstance();
                Date curDate = new Date();
                try {
                    curDate = timeFormat.parse(timeStr);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

                calendar.setTime(curDate);

                int curHour = calendar.get(Calendar.HOUR_OF_DAY);
                int curMinute = calendar.get(Calendar.MINUTE);

                return new TimePickerDialog(this,  timeSetListener,  curHour, curMinute, false);
            default:
                break;

        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            selectedCalendar.set(Calendar.MINUTE, minute);

            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        selectedDate = curDate;
        selectedHour = selectedDate.getHours();
        selectedMin = selectedDate.getMinutes();
        String selectedTimeStr = timeFormat.format(curDate);
        timeButton.setText(selectedTimeStr);
    }

    public void updateData(String url){
        class updateDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try {
                    String uri = params[0];
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("date", sqlTimeStr);
                    jsonObj.put("memo", memo);

                    BufferedWriter bufferedWriter = null;
                    Log.e("jsonerr", "write_json0 : " + jsonObj.toString());
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    Log.e("jsonerr", "write_json1 : " + jsonObj.toString());
                    con.setDoOutput(true);
                    Log.e("jsonerr", "write_json2 : " + jsonObj.toString());

                    String data ="&" + URLEncoder.encode("data", "UTF-8") + "="+ jsonObj.toString();

                    OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                    Log.e("jsonerr", "write_json3 : " + jsonObj.toString());
//                    wr.write(jsonObj.toString());//onPreExecute 메소드의 data 변수의 파라미터 내용을 POST 전송명령
                    wr.write(data);
//                    Log.e("jsonerr", "write() : ");

                    wr.flush();

                    //OutputStream os = con.getOutputStream();
                    Log.e("jsonerr", "write_json6 : " + jsonObj.toString());
                    //BufferedWriter writer = new BufferedWriter(
                      //      new OutputStreamWriter(os, "UTF-8"));
                    Log.e("jsonerr", "write_json2 : " + jsonObj.toString());
                    //os.write(jsonObj.toString().getBytes());

//                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

//                    bufferedWriter.write(jsonObj);
                    //bufferedWriter.write(jsonObj.toString());
                    Log.e("jsonerr", "write_json3 : " + jsonObj.toString());
//                    String json;
//                    while((json = bufferedReader.readLine())!= null){
//                        sb.append(json+"\n");
//                    }

                    BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line=null;
                    while((line=reader.readLine())!=null){
                        //서버응답값을 String 형태로 추가함
                        Log.e("jsonerr", "Read : " + line+"\n");
                    }





                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }


            @Override
            protected void onPostExecute(String result){
                Log.e("jsonerr", "result : "+ result);
//                myJSON=result;
//                showList();
            }
        }
        updateDataJSON g = new updateDataJSON();
        g.execute(url);
    }

}