package com.example.testbase.Notification;






import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentNotification extends Fragment {

	FragmentTabHost mTabHost1 = null;

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
		View view = inflater.inflate(R.layout.fragment_notification, null);

		initView(view);

		return view;
	}

	private void initView(View view) {

		mTabHost1 = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		
		mTabHost1.setup(getActivity(), getChildFragmentManager(),
				android.R.id.tabcontent);

		mTabHost1.addTab(mTabHost1.newTabSpec("sub11").setIndicator("课程安排"),
				NotificationCalendar.class, null);

		mTabHost1.addTab(mTabHost1.newTabSpec("sub12").setIndicator("任务提交"),
				NotificationTask.class, null);

		
		mTabHost1.getTabWidget().setDividerDrawable(R.color.white);

	}

}
