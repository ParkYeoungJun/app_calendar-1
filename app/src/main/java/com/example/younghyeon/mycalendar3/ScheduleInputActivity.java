package com.example.younghyeon.mycalendar3;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleInputActivity extends Activity {
    public static final String TAG = "ScheduleInputActivity";

    EditText messageInput;
    Button timeButton;

    ImageView weather01Button;
    ImageView weather02Button;
    ImageView weather03Button;
    ImageView weather04Button;

    int curYear, curMonth, curDay;
    int selectedHour, selectedMin;
    String sqlTimeStr = "";
    String memo = "";

    int selectedWeather = 0;

    public static final int DIALOG_TIME = 1101;

    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH시 mm분");

    Date selectedDate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_input);

        setTitle("일정 추가");

        Intent it = getIntent();
        curYear = it.getExtras().getInt("year");
        curMonth = it.getExtras().getInt("month")+1;
        curDay = it.getExtras().getInt("day");


        messageInput = (EditText) findViewById(R.id.messageInput);

        timeButton = (Button) findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TIME);
            }
        });

        weather01Button = (ImageView) findViewById(R.id.weather01Button);
        weather01Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectWeatherButton(0);
            }
        });

        weather02Button = (ImageView) findViewById(R.id.weather02Button);
        weather02Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectWeatherButton(1);
            }
        });

        weather03Button = (ImageView) findViewById(R.id.weather03Button);
        weather03Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectWeatherButton(2);
            }
        });

        weather04Button = (ImageView) findViewById(R.id.weather04Button);
        weather04Button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                selectWeatherButton(3);
            }
        });

        Button saveButton = (Button) findViewById(R.id.saveButton);
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

                postData("http://52.78.88.182/insertdata.php");

                Log.e("jsonerr", "postData");


                Intent intent = new Intent();
                intent.putExtra("time", timeStr);
                intent.putExtra("message", messageStr);
                intent.putExtra("weather", selectedWeather);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Button closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        // set selected date using current date
        Date curDate = new Date();
        setSelectedDate(curDate);

        // process the passed intent
        Intent intent = getIntent();
        String weatherIconUrl = intent.getStringExtra("weatherIconUrl");
        if (weatherIconUrl != null) {
            File iconFile = new File(weatherIconUrl);
            String iconFileName = iconFile.getName();
            Log.d(TAG, "weather icon file name : " + iconFileName);

            if (iconFileName != null) {
                if (iconFileName.equals("sunny.gif")) {
                    selectWeatherButton(0);
                } else if (iconFileName.equals("cloudy.gif")) {
                    selectWeatherButton(1);
                } else if (iconFileName.equals("rain.gif")) {
                    selectWeatherButton(2);
                } else if (iconFileName.equals("snow.gif")) {
                    selectWeatherButton(3);
                } else {
                    Log.d(TAG, "weather icon is not found.");
                    selectWeatherButton(0);
                }
            } else {
                selectWeatherButton(0);
            }
        } else {
            selectWeatherButton(0);
        }

    }

    private void selectWeatherButton(int index) {
        selectedWeather = index;

        weather01Button.setBackgroundColor(Color.WHITE);
        weather02Button.setBackgroundColor(Color.WHITE);
        weather03Button.setBackgroundColor(Color.WHITE);
        weather04Button.setBackgroundColor(Color.WHITE);

        if (selectedWeather == 0) {
            weather01Button.setBackgroundColor(Color.RED);
        } else if (selectedWeather == 1) {
            weather02Button.setBackgroundColor(Color.RED);
        } else if (selectedWeather == 2) {
            weather03Button.setBackgroundColor(Color.RED);
        } else if (selectedWeather == 3) {
            weather04Button.setBackgroundColor(Color.RED);
        }
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
//                selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
//                selectedMin = calendar.get(Calendar.MINUTE);

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

//            selectedHour = hourOfDay;
//            selectedMin = minute;

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

    public void postData(String url){
        class postDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                try {
//                    ArrayList<NameValue>

                    String uri = params[0];
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("date", sqlTimeStr);
                    jsonObj.put("memo", memo);


                    BufferedWriter bufferedWriter = null;
//                BufferedReader bufferedReader = null;
                    Log.e("jsonerr", "write_json0 : " + jsonObj.toString());
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    Log.e("jsonerr", "write_json1 : " + jsonObj.toString());
                    con.setDoOutput(true);
                    con.setDoInput(true);
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
                    Log.e("jsonerr", "before read");
                    BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.e("jsonerr", "ing read");
                    String line=null;
                    Log.e("jsonerr", "ing read");
                    while((line=reader.readLine())!=null){
                        //서버응답값을 String 형태로 추가함
                        Log.e("jsonerr", "Input Read : " + line+"\n");
                    }
                    Log.e("jsonerr", "after read");




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
        postDataJSON g = new postDataJSON();
        g.execute(url);
    }

}