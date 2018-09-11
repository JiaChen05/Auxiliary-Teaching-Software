package com.example.testbase.Task;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.testbase.MainActivity;
import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ReleaseTask extends Activity implements OnClickListener {
	private static final String NULL = null;
	private Button rt;
	private TextView ttitle,tde;
	private String title,de,type="课堂目标问题";
	private Connection con=null;
	private RadioButton type1,type2,type3,type4;
	public static int taskID=9;
	
	 public void onCreate(Bundle savedInstanceState) {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.releasetask);
	        ttitle=(TextView)this.findViewById(R.id.ttitle);
	        tde=(TextView)this.findViewById(R.id.tde);
	        rt=(Button)this.findViewById(R.id.releaset);
	        type1=(RadioButton)this.findViewById(R.id.tasktype1);
	        type2=(RadioButton)this.findViewById(R.id.tasktype2);
	        type3=(RadioButton)this.findViewById(R.id.tasktype3);
	        type4=(RadioButton)this.findViewById(R.id.tasktype4);
	        
			rt.setOnClickListener(this);
			type1.setOnClickListener(this);
			type2.setOnClickListener(this);
			type3.setOnClickListener(this);
			type4.setOnClickListener(this);
	        
	       
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
        switch (v.getId()) {
        case R.id.tasktype1:
        	type="课堂目标问题";
        	break;
        case R.id.tasktype2:
        	type="随堂测试题目";
        	break;
        	
        case R.id.tasktype3:
        	type="纸质作业题目";
        	break;
        case R.id.tasktype4:
        	type="实验课题目";
        	break;
        
        case R.id.releaset:
        	title=ttitle.getText().toString().trim();
        	de=tde.getText().toString().trim();
        	
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
        	
        	boolean result=false;
        	
        	String sql="insert into task "
        			+ "values("+taskID+",'"+ttitle+"','"+type+"','已提交','"+tde+"','"+tde+"',1,"+
        			+MainActivity.userID+","+NULL+")";
        	
    		System.out.println(sql);
    		String sql_ch="select * from task where id="+ taskID;
    		System.out.println(sql_ch);
        	try
			{
				Statement st=(Statement) con.createStatement();
				boolean rs=st.execute(sql);
				ResultSet res=st.executeQuery(sql_ch);
				if(res.next()) {
					
					     taskID++;
						result=true;	
					
				}
				res.close();
				st.close();
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
        	if(result)
        	{
        		
        	T.showShort(this, "任务发布成功！");
        	}
        	else
        		T.showShort(this, "任务发布失败！");
        	
        	
        	
        	break;
		
	}
	}

}
