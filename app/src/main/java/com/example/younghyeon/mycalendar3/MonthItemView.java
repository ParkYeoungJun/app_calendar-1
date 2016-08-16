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
	private TextView dayMsg;
	private ImageView weatherImage;

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
		weatherImage = (ImageView) findViewById(R.id.weatherImage);
		dayMsg = (TextView) findViewById(R.id.dayMsg);
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

	public void setMsg(MonthItem item, ArrayList<ScheduleListItem> aList){
		this.item = item;
		// 이건 없어도 될거같은데
		// 여기서 일정추가한거 받아와야 되겠는데

		dayMsg.setText(aList.get(0).getMessage());
		// 이거 받아와지는데 글자 제한이랑 ..은 처리 어떻게하지
	}

	public  void setMsg(int num){
		dayMsg.setText("");
	}

	public void setTextColor(int color) {
		dayText.setTextColor(color);
	}

	public void setBackgroundColor(int color) {
		itemContainer.setBackgroundColor(color);
	}

}
