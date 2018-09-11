package com.example.testbase;





import com.example.testbase.My.FragmentMy;
import com.example.testbase.Notification.FragmentNotification;
import com.example.testbase.Task.FragmentTask;
import com.example.testbase.evaluation.FragmentEvaluation;
import com.example.testbase.fragment3.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;



public class MainTab extends FragmentActivity {
	private String TAG = MainTab.class.getName();
	
	

	public TabFragmentHost mTabHost;

	private String[] TabTag = { "tab1", "tab2", "tab3", "tab4" };
	
	private Integer[] ImgTab = { R.layout.tab_notification,
			R.layout.tab_task,R.layout.tab_share,R.layout.tab_my };


	private Class[] ClassTab = { FragmentNotification.class, FragmentTask.class,
			FragmentEvaluation.class, FragmentMy.class };


	private Integer[] StyleTab = { R.color.white, R.color.white, R.color.white,
			R.color.white };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maintabs);
		setupView();
		initValue();
		setLinstener();
		fillData();

	}

	private void setupView() {

		mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(),
				android.R.id.tabcontent);

	}

	private void initValue() {

		InitTabView();
	}

	private void setLinstener() {
		// imv_back.setOnClickListener(this);

	}

	private void fillData() {
		// TODO Auto-generated method stub

	}

	// 初始化 tab 自定义的选项卡 ///////////////
	private void InitTabView() {

		// 可以传递参数 b;传递公共的userid,version,sid
		Bundle b = new Bundle();
		// 循环加入自定义的tab
		for (int i = 0; i < TabTag.length; i++) {
			// 封装的自定义的tab的样式
			View indicator = getIndicatorView(i);
			mTabHost.addTab(
					mTabHost.newTabSpec(TabTag[i]).setIndicator(indicator),
					ClassTab[i], b);// 传递公共参数

		}
		mTabHost.getTabWidget().setDividerDrawable(R.color.white);
	}

	// 设置tab自定义样式:注意 每一个tab xml子布局的linearlayout 的id必须一样
	private View getIndicatorView(int i) {
		// 找到tabhost的子tab的布局视图
		View v = getLayoutInflater().inflate(ImgTab[i], null);
		LinearLayout tv_lay = (LinearLayout) v.findViewById(R.id.layout_back);
		tv_lay.setBackgroundResource(StyleTab[i]);

		return v;
	}
}
