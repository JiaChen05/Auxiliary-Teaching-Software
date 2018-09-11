package com.example.testbase.My;





import com.example.testbase.MainActivity;
import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.content.Context;  
import android.widget.BaseExpandableListAdapter;  
import android.widget.ExpandableListView;  
import android.widget.ImageView;  
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;  
import java.util.List; 

public class FragmentMy_t extends Fragment {
	private ExpandableListView expandableListView;  
	private List<String> Group;  
	private List<List<String>> Child;  
	private List<String> ChildFirst;  
	private List<String> ChildSecond;  
	private TextView myname;

	 private Connection con=null;
	 
    public FragmentMy_t() {  
		}  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_my, null);
		T.showShort(getActivity(), "FragmentMy==onCreateView");
		myname=(TextView)view.findViewById(R.id.myname);
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
		ChildSecond = new ArrayList<String>();
		Child = new ArrayList<List<String>>(); 
		 
	    Group.add("�ҷ�����֪ͨ");  
	    Group.add("�ҷ���������");  
	    Group.add("�˳���¼");  
	    
	    String sql1="select * from notice where senderId="+MainActivity.userID;
	    String sql2="select * from task where creatorId="+MainActivity.userID;
	    String sql3="select * from teacher where id="+MainActivity.userID;
	    System.out.println(sql1); 
	    System.out.println(sql2); 
		try {
			Statement st2=(Statement)con.createStatement();
			ResultSet rs2=st2.executeQuery(sql3);
			rs2.next();
			myname.setText(rs2.getString("name"));
			rs2.close();
			st2.close();
			
			Statement st1=(Statement)con.createStatement();
			ResultSet rs1=st1.executeQuery(sql1);
			while(rs1.next()){
				ChildFirst.add(rs1.getString("name"));
			}
			rs1.close();
			st1.close();
			
			Statement st=(Statement)con.createStatement();
			ResultSet rs=st.executeQuery(sql2);
			while(rs.next()){
				ChildSecond.add(rs.getString("name"));	 
			}
			rs.close();
			st.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
        Child.add(ChildFirst);
        Child.add(ChildSecond);
        
	expandableListView = (ExpandableListView)view.findViewById(R.id.Info); 
	expandableListView.setAdapter(new MyExpandableListViewAdapter(getActivity())); 
	expandableListView.setGroupIndicator(null); 
	

		return view;
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
	    GroupHolder groupHolder = null;     //�����Լ��趨��һ�����࣬�����洢�ؼ��������Ϣ  
	    if (convertView == null) {          //�����convertView��ʵ��һ���𻺳����õģ����ߣ���Ϊ��һ��item����Ļ�й��������ǰ����ŵ�convertView��                                                 //�������ٻ�������ʱ�����ֱ��ȥȡ���������´���������Ҳ�Ƽ�һ����ַ����ҿ�����ϸ�˽�  
		convertView = (View) getActivity().getLayoutInflater().from(context).inflate(  
				                R.layout.group_view, null);      //�ѽ���ŵ�������  
				        groupHolder = new GroupHolder();          //ʵ�������Ǵ����������  
				        groupHolder.txt = (TextView) convertView.findViewById(R.id.notice_item_date);  //ʵ���������TextView  
				        convertView.setTag(groupHolder);                                 //��view����һ����ǩ�����߼���������Ѿ��ڻ����������һ��view���»�ֱ                                                                               //�����þ�����  
				    } else {  
				        groupHolder = (GroupHolder) convertView.getTag();     //Ȼ������ֱ������  
				    }  
				    groupHolder.txt.setText(Group.get(groupPosition));//�������Ӧ��group����������Ӧ��Text  
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
	        //itemHolder.img.setBackgroundResource(ChildPicture.get(groupPosition).get(  
	               // childPosition));  
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
