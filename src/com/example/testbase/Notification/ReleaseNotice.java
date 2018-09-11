package com.example.testbase.Notification;


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

public class ReleaseNotice extends Activity implements OnClickListener {
	private static final String NULL = null;
	private Button rn;
	private TextView ntitle,nde;
	private String title,de,type="课程安排";
	private Connection con=null;
	private RadioButton aboutC,aboutT;
	public static int noticeID=8;
	
	 public void onCreate(Bundle savedInstanceState) {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.releasenotice);
	        ntitle=(TextView)this.findViewById(R.id.ntitle);
	        nde=(TextView)this.findViewById(R.id.nde);
	        rn=(Button)this.findViewById(R.id.releasen);
	        aboutC=(RadioButton)this.findViewById(R.id.aboutCourse);
	        aboutT=(RadioButton)this.findViewById(R.id.aboutTask);
	        
			rn.setOnClickListener(this);
			aboutC.setOnClickListener(this);
			aboutT.setOnClickListener(this);
	        
	       
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
        switch (v.getId()) {
        case R.id.aboutCourse:
        	type="课程安排";
        	break;
        case R.id.aboutTask:
        	type="任务提交";
        	break;
        
        case R.id.releasen:
        	title=ntitle.getText().toString().trim();
        	de=nde.getText().toString().trim();
        	
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
        	
        	String sql="insert into notice "
        			+ "values("+noticeID+",'"+title+"','"+type+"',1,"+MainActivity.userID+",'"+de+"',"+NULL+")";
        	
    		System.out.println(sql);
    		String sql_ch="select * from notice where id="+ noticeID;
    		System.out.println(sql_ch);
        	try
			{
				Statement st=(Statement) con.createStatement();
				boolean rs=st.execute(sql);
				ResultSet res=st.executeQuery(sql_ch);
				if(res.next()) {
					
					     noticeID++;
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
        		
        	T.showShort(this, "通知发布成功！");
        	}
        	else
        		T.showShort(this, "通知发布失败！");
        	
        	
        	
        	break;
		
	}
	}

}
