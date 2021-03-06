package com.example.younghyeon.mycalendar3;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MonthItemView extends RelativeLayout {
	private Context mContext;

	private MonthItem item;

	private RelativeLayout itemContainer;
	private TextView dayText;
	private TextView dayMsg1;
	private TextView dayMsg2;
	private TextView dayMsg3;
	private TextView dayMsg4;
	private ImageView weatherImage;
	private String tempStr = "";
	private String resultStr = "1234..";
//	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

	public MonthItemView(Context context) {
		super(context);

		mContext = context;

		init();
	}

	public MonthItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.month_item, this, true);



		itemContainer = (RelativeLayout) findViewById(R.id.itemContainer);
		dayText = (TextView) findViewById(R.id.dayText);
//		dayText.setLayoutParams(params);
		weatherImage = (ImageView) findViewById(R.id.weatherImage);
		dayMsg1 = (TextView) findViewById(R.id.dayMsg1);
		dayMsg2 = (TextView) findViewById(R.id.dayMsg2);
		dayMsg3 = (TextView) findViewById(R.id.dayMsg3);
		dayMsg4 = (TextView) findViewById(R.id.dayMsg4);
		itemContainer.setBackgroundColor(Color.WHITE);
	}


	public MonthItem getItem() {
		return item;
	}

	public void setItem(MonthItem item) {
		this.item = item;

		int day = item.getDay();
		if (day != 0) {
			dayText.setText(String.valueOf(day));
		} else {
			dayText.setText("");
		}

	}

	public void setWeatherImage(int resId) {
		//weatherImage.setImageResource(resId);
		//날짜안에 그림 이미지 넣어지는건데
	}

	public void setMsg(MonthItem item, ArrayList<ScheduleListItem> aList, int size){
		this.item = item;
		// 이건 없어도 될거같은데
		// 여기서 일정추가한거 받아와야 되겠는데

		tempStr = "";
		resultStr = "1234..";

		if(size == 1) {
			tempStr = aList.get(0).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);

//			dayMsg1.setLayoutParams(params);
			dayMsg1.setText(tempStr);
			dayMsg1.setVisibility(VISIBLE);
//			dayMsg1.setLayoutParams(params);

//			}
//			else {
//				dayMsg1.setText(tempStr);
//			}
			dayMsg2.setText("");
			dayMsg2.setVisibility(INVISIBLE);
			dayMsg3.setText("");
			dayMsg3.setVisibility(INVISIBLE);
			dayMsg4.setText("");
			dayMsg4.setVisibility(INVISIBLE);
		}
		else if(size == 2) {
			tempStr = aList.get(0).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg1.setText(resultStr + "..");
//
//			}
//			else {
				dayMsg1.setText(tempStr);
			dayMsg1.setVisibility(VISIBLE);
//			}

			tempStr = aList.get(1).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg2.setText(resultStr + "..");

//			}
//			else {
				dayMsg2.setText(tempStr);
			dayMsg2.setVisibility(VISIBLE);
//			}
			dayMsg3.setText("");
			dayMsg4.setText("");
			dayMsg3.setVisibility(INVISIBLE);
			dayMsg4.setVisibility(INVISIBLE);
		}
		else if(size == 3) {
			tempStr = aList.get(0).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg1.setText(resultStr + "..");
//
//			}
//			else {
				dayMsg1.setText(tempStr);
			dayMsg1.setVisibility(VISIBLE);
//			}

			tempStr = aList.get(1).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg2.setText(resultStr + "..");
//
//			}
//			else {
				dayMsg2.setText(tempStr);
			dayMsg2.setVisibility(VISIBLE);
//			}
//
			tempStr = aList.get(2).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg3.setText(resultStr + "..");
//			}
//			else {
				dayMsg3.setText(tempStr);
			dayMsg3.setVisibility(VISIBLE);
//			}
			dayMsg4.setText("");
			dayMsg4.setVisibility(INVISIBLE);
		}
		else if(size >= 4) {
			tempStr = aList.get(0).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg1.setText(resultStr + "..");
//
//			}
//			else {
				dayMsg1.setText(tempStr);
			dayMsg1.setVisibility(VISIBLE);
//			}

			tempStr = aList.get(1).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg2.setText(resultStr + "..");
//
//			}
//			else {
				dayMsg2.setText(tempStr);
			dayMsg2.setVisibility(VISIBLE);
//			}

			tempStr = aList.get(2).getMessage();
//			if ( tempStr.length() > 4){
//				resultStr = tempStr.substring(0,4);
//				dayMsg3.setText(resultStr + "..");
//			}
//			else {
				dayMsg3.setText(tempStr);
			dayMsg3.setVisibility(VISIBLE);
//			}
			tempStr = aList.get(3).getMessage();
			dayMsg4.setText("..");
//			dayMsg4.setVisibility(INVISIBLE);
		}
	}

	public  void setMsg(ArrayList<ScheduleListItem> aList){
		dayMsg1.setText("");
		dayMsg2.setText("");
		dayMsg3.setText("");
		dayMsg4.setText("");
		dayMsg1.setVisibility(INVISIBLE);
		dayMsg2.setVisibility(INVISIBLE);
		dayMsg3.setVisibility(INVISIBLE);
		dayMsg4.setVisibility(INVISIBLE);
	}

	public void setTextColor(int color) {
		dayText.setTextColor(color);
	}

	public void setBackgroundColor(int color) {
		itemContainer.setBackgroundColor(color);
	}

}
