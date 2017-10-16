package com.my_movingbricks.view.fragment;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.my_movingbricks.R;
import com.my_movingbricks.app.APP;
import com.my_movingbricks.base.BaseMvpFragment;
import com.my_movingbricks.base.BasePresenter;
import com.my_movingbricks.indicator.TabPageIndicator;
import com.my_movingbricks.view.adapter.PersonalLetterTabFragmentAdapter;

import java.util.ArrayList;

/**
 * 首页的fragment
 *
 * @author itlanbao.com
 */
public class HomeFragment extends BaseMvpFragment {

	/*
	 * 注意：有的引入控件TabPageIndicator后效果不一样需要在当前activity里面引入android:theme=
	 * "@style/StyledIndicators"
	 * 即，我们这里需要对HomeActivity里面的清单列表文件添加android:theme="@style/StyledIndicators"
	 */
	private TabPageIndicator indicator;

	private ViewPager mPager;

	private ArrayList<BaseMvpFragment> fragmentsList;

	private String[] tabContent = null;

	@Override
	protected void onVisible(boolean isInit) {
		// 这个方法的目的就是当页面可见的时候才初始化，目的是避免fragment切换的时候每次都加载，
		// 这里面控制加载过的数据切换页面的时候就不用在加载数据了
		if (isInit) {
			//initView();// 初始化控件
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_shu;
	}

	@Override
	protected void initView(View view) {
		//mActivity = this.getActivity();// 初始化上下文
		mPager = (ViewPager) view.findViewById(R.id.pager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		if (fragmentsList == null) {
			fragmentsList = new ArrayList<>();
		}
		fragmentsList.add(new NewestFragment());
		fragmentsList.add(new TheHottestFragment());
		fragmentsList.add(new RecommendFragment());
		fragmentsList.add(new SpecialFragment());
		if (tabContent == null) {
			tabContent = new String[] {getResources().getString(R.string.tv_top1), getResources().getString(R.string.tv_top2), getResources().getString(R.string.tv_top3),getResources().getString(R.string.tv_top4)};
		}
		final FragmentPagerAdapter adapter = new PersonalLetterTabFragmentAdapter(getChildFragmentManager());
		((PersonalLetterTabFragmentAdapter) adapter).setData(tabContent, fragmentsList);
		mPager.setAdapter(adapter);
		// 控制内存最多有几个页面
		mPager.setOffscreenPageLimit(fragmentsList.size());
		mPager.setCurrentItem(0);
		indicator.setViewPager(mPager);
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 这个是设置左滑切换底部view的边界，必须要设置
				APP.getInstance().setBorderViewPosition(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	/**
	 * 清空Fragment栈
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		int backStackCount = getFragmentManager().getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {
			getFragmentManager().popBackStack();
		}
	}

	@Override
	public BasePresenter initPresenter() {
		return null;
	}

	@Override
	protected boolean isToolbarShow() {
		return false;
	}
}
