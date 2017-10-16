package com.my_movingbricks.view.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.my_movingbricks.R;

public class ContainLoadViewLayout extends LinearLayout {
	
	private LoadingView mLoadingView;
	private ImageView mOval;
	
	private int mFallDistance;

	public ContainLoadViewLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ContainLoadViewLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ContainLoadViewLayout(Context context) {
		this(context,null);
	}
	
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_loading,null);
		mLoadingView = (LoadingView) view.findViewById(R.id.shapeLoadingView); //当前正在绘制的图形
		mOval = (ImageView) view.findViewById(R.id.bottom_shadow); //用来显示底部的阴影
		addView(view);

		mFallDistance = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 70, getContext().getResources()
						.getDisplayMetrics());

		//加载完成布局结束以后启动下落的动画
		startFall();
	}


	/**
	 * 下落的动画
	 */
	public void startFall() {
		ObjectAnimator rectAnimator = ObjectAnimator.ofFloat(mLoadingView, "translationY", mFallDistance,0);
		ObjectAnimator ovalAnimator = ObjectAnimator.ofFloat(mOval, "scaleX", 1.0f,0.3f);
		rectAnimator.setInterpolator(new DecelerateInterpolator());
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(1000);
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				startUp();
			}
		});

		animatorSet.play(rectAnimator).with(ovalAnimator);
		animatorSet.start();
	}

	/**
	 * 弹跳起来的动画
	 */
	public void startUp() {
		ObjectAnimator rectUpAnimator = ObjectAnimator.ofFloat(mLoadingView, "translationY", 0,mFallDistance);
		ObjectAnimator rectRotateAnimator = ObjectAnimator.ofFloat(mLoadingView, "rotation", 0,180);
		if (mLoadingView.getCurrentShape() == LoadingView.Shape.RACTANGLE) {//如果是三角形，反向旋转
			rectRotateAnimator = ObjectAnimator.ofFloat(mLoadingView, "rotation", 0,-180);
		}
		ObjectAnimator ovalAnimator = ObjectAnimator.ofFloat(mOval, "scaleX", 0.3f,1.0f);
		rectUpAnimator.setInterpolator(new AccelerateInterpolator());
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.setDuration(1000);
		animatorSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				//在弹跳动画完成之后，调用changeShape，该方法会重新设置当前需要绘制的图形，并重绘该图形
				mLoadingView.changeShape(mLoadingView.getCurrentShape());
				//交错执行上升和下落的动画
				startFall();
			}
		});
		animatorSet.play(rectUpAnimator).with(rectRotateAnimator).with(ovalAnimator);
		animatorSet.start();
	}
	
	
}
