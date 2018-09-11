package com.example.testbase.evaluation;






import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FragmentEvaluation extends Fragment {

	FragmentTabHost mTabHost = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Bundle bundle = getArguments();
		if (null != bundle) {
			//
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		T.showShort(getActivity(), "FragmentMessage==onCreateView");
		View view = inflater.inflate(R.layout.fragment_evaluation, null);

		initView(view);

		return view;
	}

	private void initView(View view) {

		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("sub1").setIndicator("自我评价"),
				SelfEvaluation.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("sub2").setIndicator("小组互评"),
				GroupEvaluation.class, null);

		mTabHost.addTab(mTabHost.newTabSpec("sub3").setIndicator("任务评价"),
				TaskEvaluation.class, null);
		
		mTabHost.getTabWidget().setDividerDrawable(R.color.white);

	}

}
