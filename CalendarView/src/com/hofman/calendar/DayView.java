package com.hofman.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class DayView extends View {
	Paint mPaint = new Paint();
	private int day = 0;
	private boolean hasX = false;
	private boolean isToday = false;
	private boolean greyed = false;
	private int circleRadius = 50;

	public DayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DayView(Context mContext) {
		super(mContext);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Style.FILL);
		canvas.drawPaint(mPaint);
		if (greyed) {
			mPaint.setColor(Color.LTGRAY);
		} else {
			mPaint.setColor(Color.BLACK);
		}
		if (hasX) {
			drawX(canvas);
		} else {
			mPaint.setTextSize(50);
			canvas.drawText("" + (day), this.getWidth()/3, this.getHeight()/1.5f, mPaint);

		}
		if (isToday) {
			drawCircle(canvas);
		}
	}

	protected void setDay(int day, boolean grey) {
		this.day = day;
		if (grey) {
			greyed = true;
		}
	}

	private void drawCircle(Canvas canvas) {
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(10);
		canvas.drawCircle(this.getWidth()/2, this.getHeight()/2f, circleRadius, mPaint);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeWidth(0);
	}

	private void drawX(Canvas canvas) {
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(10);
		canvas.drawLine(this.getWidth(), 0, 0, this.getHeight(), mPaint);
		canvas.drawLine(0, 0, this.getWidth(), this.getHeight(), mPaint);
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(0);
	}

	protected void setToday() {
		isToday = true;
	}

	protected void setRedX() {
		hasX = true;
	}

}
