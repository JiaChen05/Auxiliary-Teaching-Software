package com.example.testbase.Task;





import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.example.testbase.Task.spiner.widget.AbstractSpinerAdapter;
import com.example.testbase.Task.spiner.widget.SpinerPopWindow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentTask extends Fragment implements OnClickListener, AbstractSpinerAdapter.IOnItemSelectListener {
	

	private View mRootView;
	private TextView mTView;
	private ImageButton mBtnDropDown;
	private List<String> nameList = new ArrayList<String>();
	private ExpandableListView expandableListView;  
	private List<String> Group;  
	private List<List<String>> Child;  
	private List<String> ChildFirst;  
	private Connection con=null;
	
	public FragmentTask() {  
	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.fragment_task, container, false); 

		T.showShort(getActivity(), "FragmentMy==onCreateView");
         mRootView =view.findViewById(R.id.rootView);
		
    	mTView = (TextView)view.findViewById(R.id.tv_value);
		mBtnDropDown = (ImageButton)view.findViewById(R.id.bt_dropdown);
		mBtnDropDown.setOnClickListener(this);
		String[] names = {"课堂目标问题","随堂测试题目","纸质作业题目","实验课题目"};
		for(int i = 0; i < names.length; i++){
			nameList.add(names[i]);
		}
		mSpinerPopWindow = new SpinerPopWindow(getActivity());
		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
		setText(0);
		 try  
		    {
		      Class.forName("com.mysql.jdbc.Driver");     
		      con = (Connection) DriverManager.getConnection(
				      "jdbc:mysql://10.0.2.2:3306/coursesupport","user","123456");
				          
			  System.out.println("Success connect Mysql server!");
		    }catch( SQLException ee) 
		    {
		    	System.out.println("Error"+ee.getMessage()); 
		    	ee.printStackTrace();
		    }
			catch (Exception e)
		    {
		      System.out.print("Error loading Mysql Driver!"); 
		      e.printStackTrace(); 
		    }
		Group = new ArrayList<String>();
		ChildFirst = new ArrayList<String>();  
		Child = new ArrayList<List<String>>();  
		
		try {
			Statement st=(Statement)con.createStatement();
			String sql="select * from task where type= '课堂目标问题'";
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				//System.out.println(rs.getString("description")); 
				Group.add(rs.getString("name")); 
				ChildFirst.add(rs.getString("description"));
				Child.add(ChildFirst);
				//ChildFirst.clear();
				ChildFirst = new ArrayList<String>();  
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
 	    expandableListView = (ExpandableListView)view.findViewById(R.id.my_phone); 
		expandableListView.setAdapter(new MyExpandableListViewAdapter(getActivity())); 
		expandableListView.setGroupIndicator(null); 

		return view;
	}
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.bt_dropdown:
			showSpinWindow();
			break;
		}
	}
	

	private void setText(int pos){
		if (pos >= 0 && pos <= nameList.size()){
			String value = nameList.get(pos);
		
			mTView.setText(value);
		}
	}

	
	private SpinerPopWindow mSpinerPopWindow;
	private void showSpinWindow(){
		Log.e("", "showSpinWindow");
		mSpinerPopWindow.setWidth(mTView.getWidth());
		mSpinerPopWindow.showAsDropDown(mTView);
	}


	@Override
	public void onItemClick(int pos) {
		setText(pos);
		try  
	    {
	      Class.forName("com.mysql.jdbc.Driver");     
	      con = (Connection) DriverManager.getConnection(
			      "jdbc:mysql://10.0.2.2:3306/coursesupport","user","123456");
			          
		  System.out.println("Success connect Mysql server!");
	    }catch( SQLException ee) 
	    {
	    	System.out.println("Error"+ee.getMessage()); 
	    	ee.printStackTrace();
	    }
		catch (Exception e)
	    {
	      System.out.print("Error loading Mysql Driver!"); 
	      e.printStackTrace(); 
	    }
	Group = new ArrayList<String>();
	ChildFirst = new ArrayList<String>();  
	Child = new ArrayList<List<String>>();  
	
	try {
		Statement st=(Statement)con.createStatement();
		String sql="select * from task where type= '"+nameList.get(pos)+"'";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			//System.out.println(rs.getString("description")); 
			Group.add(rs.getString("name")); 
			ChildFirst.add(rs.getString("description"));
			Child.add(ChildFirst);
			//ChildFirst.clear();
			ChildFirst = new ArrayList<String>();  
		}
		rs.close();
		st.close();
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
	  
	 expandableListView.setAdapter(new MyExpandableListViewAdapter(getActivity())); 
	 expandableListView.setGroupIndicator(null); 
		
		
	}
	
	
	class MyExpandableListViewAdapter extends BaseExpandableListAdapter {  
		  
	    private Context context;  
	  
	    public MyExpandableListViewAdapter(Context context) {  
	        this.context = context;  
	    }  
	  
	    @Override  
	    public int getGroupCount() {  
	        return Group.size();  
	    }  
	  
	    @Override  
	    public int getChildrenCount(int groupPosition) {  
	        return Child.get(groupPosition).size();  
	    }  
	  
	    @Override  
	    public Object getGroup(int groupPosition) {  
	        return Group.get(groupPosition);  
	    }  
	  
	    @Override  
	    public Object getChild(int groupPosition, int childPosition) {  
	        return Child.get(groupPosition).get(childPosition);  
	    }  
	  
	    @Override  
	    public long getGroupId(int groupPosition) {  
	        return groupPosition;  
	    }  
	  
	    @Override  
	    public long getChildId(int groupPosition, int childPosition) {  
	        return childPosition;  
	    }  
	  
	    @Override  
	    public boolean hasStableIds() {  
	        return true;  
	    }  

	
	@Override  
	public View getGroupView(int groupPosition, boolean isExpanded,  
	                         View convertView, ViewGroup parent) {  
	    GroupHolder groupHolder = null;     //我们自己设定的一个简单类，用来存储控件的相关信息  
	    if (convertView == null) {          //这里的convertView其实是一个起缓冲作用的，工具，因为当一个item从屏幕中滚出，我们把它放到convertView里                                                 //，这样再滑回来的时候可以直接去取，不用重新创建，这里也推荐一个网址，大家可以详细了解  
		convertView = (View) getActivity().getLayoutInflater().from(context).inflate(  
				                R.layout.group_view, null);      //把界面放到缓冲区  
				        groupHolder = new GroupHolder();          //实例化我们创建的这个类  
				        groupHolder.txt = (TextView) convertView.findViewById(R.id.notice_item_date);  //实例化类里的TextView  
				        convertView.setTag(groupHolder);                                 //给view对象一个标签，告诉计算机我们已经在缓冲区里放了一个view，下回直                                                                               //接来拿就行了  
				    } else {  
				        groupHolder = (GroupHolder) convertView.getTag();     //然后他就直接来拿  
				    }  
				    groupHolder.txt.setText(Group.get(groupPosition));//最后在相应的group里设置他相应的Text  
				    return convertView;  
				} 
	
	@Override  
	    public View getChildView(int groupPosition, int childPosition,  
	                             boolean isLastChild, View convertView, ViewGroup parent) {  
	        ItemHolder itemHolder = null;  
	        if (convertView == null) {  
	            convertView = (View) getActivity().getLayoutInflater().from(context).inflate(  
	                    R.layout.child_view, null);  
	            itemHolder = new ItemHolder();  
	            itemHolder.txt = (TextView) convertView.findViewById(R.id.group);  
	            //itemHolder.img = (ImageView) convertView.findViewById(R.id.iv);  
	            convertView.setTag(itemHolder);  
	        } else {  
	            itemHolder = (ItemHolder) convertView.getTag();  
	        }  
	        itemHolder.txt.setText(Child.get(groupPosition).get(  
	                childPosition));  
	       // itemHolder.img.setBackgroundResource(ChildPicture.get(groupPosition).get(  
	             //   childPosition));  
	        return convertView;  
	    }  
	  
	    @Override  
	    public boolean isChildSelectable(int groupPosition, int childPosition) {  
	        return true;  
	    }  
	  
	}  
	  
	class GroupHolder {  
	    public TextView txt;  
	    public ImageView img;  
	}  
	  
	class ItemHolder {  
	    //public ImageView img;  
	    public TextView txt;  
	}  
}
