package com.hofman.calendar;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
//TODO: Fix bug: Replicate by tilting phone horizontal, scrolling down, and tilting back. First sunday is greyed
//TODO: Comment
//TODO: Add Sun-Sat tags along the top
public class DayAdapter extends BaseAdapter {
	private Context mContext;
	private int width, height;
	private int maximumDays = 0;
	private int previousMonthMax = 0;
	private int displayYear = 2013;
	private int currYear = 2013;
	private int nextMonthDay = 1;
	int dayToStart = 0, today = 0;
	private String displayMonthName = "", currMonthName = "";
	
	public DayAdapter(Context c) {
		mContext = c;
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
	}
	
	public int getCount() {
		return 42;
	}
	
	public Object getItem(int position) {
		return null;
	}
	
	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent ) {
		DayView dView;
		int displayDate = 1;
		boolean grey = false;

		if (dayToStart == 1) {
			dayToStart += 7;
		}
		if(position + 1 < dayToStart) {
			displayDate = previousMonthMax - (dayToStart - 2) + position;
			grey = true;
		} else if (position + 1> maximumDays + dayToStart - 1)  {
			displayDate = nextMonthDay;
			grey = true;
			nextMonthDay++;
		} else {
			displayDate = position + 2 - dayToStart;
		}

		if (convertView == null) {
			dView = new DayView(mContext);
			dView.setPadding(8,8,8,8);
			
			if (width > height) {
				dView.setLayoutParams(new GridView.LayoutParams(width/7,height/7));
			} else {
				dView.setLayoutParams(new GridView.LayoutParams(width/7,width/7));
			}
		} else {
			dView = (DayView) convertView;			
		}
		
		dView.setDay(displayDate, grey);
		if (displayMonthName.equals(currMonthName) && today == displayDate && currYear == displayYear) {
			dView.setToday();
		}
		
		//TODO: Add RedXCode here
		if (displayDate == 32) {
			dView.setRedX();
		}
		
		return dView;
	}
	
	protected void setShownValues(String monShownName, int startDay, int maxDays, int prevMonth, int currShowYear) {
		displayMonthName = monShownName;
		dayToStart = startDay;
		maximumDays = maxDays;
		previousMonthMax = prevMonth;
		displayYear = currShowYear;
	}

	protected void setCurrentDate(String currMonName, int currYear, int currDate) {
		this.today = currDate;
		this.currYear = currYear;
		this.currMonthName = currMonName;
	}

}
