package com.my_movingbricks.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View {

	private Paint mPaint;
	private Shape mCurrentShape = Shape.RECT; //第一次绘制矩形
	private Path mPath;

	//定义一个枚举，用来标识当前需要绘制的形状类型
	public enum Shape{
		CIRCLE,
		RECT,
		RACTANGLE
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//一些初始化工作
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPath = new Path();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LoadingView(Context context) {
		this(context,null);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//使用switch来判断当前需要绘制图形的形状
		switch (mCurrentShape) {
			case RECT:
				mPaint.setColor(Color.parseColor("#FF9999"));
				canvas.drawRect(0,0,getWidth(),getHeight(), mPaint);
				break;
			case CIRCLE:
				int circleRadius = Math.min(getWidth(), getHeight()) / 2;
				mPaint.setColor(Color.parseColor("#99FFCC"));
				canvas.drawCircle(getWidth() / 2, getHeight() / 2 ,circleRadius, mPaint);
				break;
			case RACTANGLE:
				mPaint.setColor(Color.parseColor("#99CCFF"));
				mPath.reset();
				mPath.moveTo(getWidth() / 2,0);
				mPath.lineTo(0,getHeight());
				mPath.lineTo(getWidth(), getHeight());
				mPath.close();
				canvas.drawPath(mPath, mPaint);
				break;
			default:
				break;
		}
	}

	/**
	 * 改变当前需要绘制的形状，该接口是提供给外边调用的
	 * @param currentShape 当前已经绘制的形状
	 */
	public void changeShape(Shape currentShape) {
		if (currentShape == Shape.RECT) {
			mCurrentShape = Shape.CIRCLE;
		} else	if (currentShape == Shape.CIRCLE) {
			mCurrentShape = Shape.RACTANGLE;
		} else 	if (currentShape == Shape.RACTANGLE) {
			mCurrentShape = Shape.RECT;
		}
		//更改完成图形的形状之后，记得重绘
		invalidate();
	}

	/**
	 * 获得当前绘制的形状
	 * @return
	 */
	public Shape getCurrentShape() {
		return mCurrentShape;
	}
}
