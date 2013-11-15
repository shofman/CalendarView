package com.hofman.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class ScreenSlideCalFragment extends android.support.v4.app.Fragment {
	public static final String ARG_PAGE = "page";
	
	private int mPageNumber;
	private static int mainPage;
	
	private Calendar mCalendar = Calendar.getInstance();
	private Calendar previousMonth = Calendar.getInstance();
	private Locale english = new Locale("EN");
	private static int showYear, showMonth;
	private static int currYear, currDay;
	private static String showMonthName = "", currMonthName = "";
	
	public static ScreenSlideCalFragment create(int pageNumber) {
		ScreenSlideCalFragment fragment = new ScreenSlideCalFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ScreenSlideCalFragment() {
	}
	
	public static void setTotalPages(int pages) {
		mainPage = pages/2;
	}
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);
		setupCalendar();
		GridView gv = (GridView) rootView.findViewById(R.id.gridview);
		gv.setBackgroundColor(Color.LTGRAY);
		DayAdapter dAdapter = new DayAdapter(getActivity().getApplicationContext());
		gv.setAdapter(dAdapter);
		registerForContextMenu(gv);
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {

	        @Override
	        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				Log.d("Press", "Long was here");
	        	getActivity().openContextMenu(v.getRootView());
	            return false;
	        }
	    });
	    gv.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Log.d("Press", "Short was here");
	            //Toast.makeText(getActivity().getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
	        	DayView myDView = (DayView) v;
	        	myDView.setRedX();
	        	myDView.invalidate();
	        }
	    });
	    
	    int maxDays = mCalendar.getActualMaximum(Calendar.DATE);
		int startDay = mCalendar.get(Calendar.DAY_OF_WEEK);
		int previousMonthEndDate = previousMonth.getActualMaximum(Calendar.DATE);
		dAdapter.setShownValues(showMonthName, startDay, maxDays, previousMonthEndDate, showYear);
		dAdapter.setCurrentDate(currMonthName, currYear, currDay);
	    
		TextView tv = (TextView) rootView.findViewById(R.id.monthName);
		tv.setText(showMonthName + ", " + showYear);
		
		return rootView;
	}
	
	private void setupCalendar() {
		//Gets to the current month, but at the start to create the calendar
		showYear = mCalendar.get(Calendar.YEAR);
		showMonth = mCalendar.get(Calendar.MONTH);
		currYear = mCalendar.get(Calendar.YEAR);
		currDay = mCalendar.get(Calendar.DATE);

		int valueToAdd = showMonth - mainPage;
		showMonth = (mPageNumber + valueToAdd) % 12;
		int changeYearValue;
		if (showMonth >= 0) {
			changeYearValue = (int) ((float) (mPageNumber + valueToAdd)) / 12;
		} else {
			changeYearValue = (int) ((float) (mPageNumber + valueToAdd) - 12) / 12;
			showMonth += 12;
		}
		showYear += changeYearValue;
		currDay = mCalendar.get(Calendar.DATE);
		currMonthName = new SimpleDateFormat("MMMM", english).format(mCalendar.getTime());
		mCalendar.set(showYear, showMonth, 1);
		showMonthName = new SimpleDateFormat("MMMM", english).format(mCalendar.getTime());
		
		previousMonth.set(showYear, showMonth-1, 1);

	}
	
}
