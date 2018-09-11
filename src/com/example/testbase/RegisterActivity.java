package com.example.testbase;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class RegisterActivity extends Activity implements OnClickListener {
	private RadioButton is,it;
	private TextView raccount,rpassword1,rpassword2;
	private Button register;
	private String account,password1,password2,queryTable="student";
    
     private Connection con=null;
     public static int studentID=4;
     public static int teacherID=4;
	 private int usertype=1,userID=studentID;
	 
	 public void onCreate(Bundle savedInstanceState) {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register);
	        raccount = (TextView) this.findViewById(R.id.raccount);
	        rpassword1 = (TextView) this.findViewById(R.id.rpassword1);
	        rpassword2 = (TextView) this.findViewById(R.id.rpassword2);
	        register = (Button) this.findViewById(R.id.register);
	        
	        is.setOnClickListener(this);
	        it.setOnClickListener(this);
	        register.setOnClickListener(this);
	      
	        
	       
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  switch (v.getId()) {
	        case R.id.register:
	        	
	        	boolean result=false;
	        	account=raccount.getText().toString().trim();
	        	password1=rpassword1.getText().toString().trim();
	        	password2=rpassword2.getText().toString().trim();
	        	if(!password1.equals(password2))
	          	{
	          		
	          	T.showShort(this, "两次输入密码不一致！");
	          	}
	          	else
	          	{
	          	
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
	              
	        	
	        	
	          	String sql="insert into "+queryTable
	          			+ " values("+userID+",'"+account+"','"+password1+"','"+account+"',"+null+","+null+","+null+","+null+")";
	          	
	      		System.out.println(sql);
	      		String sql_ch="select * from "+ queryTable+" where id="+ userID;
	      		System.out.println(sql_ch);
	          	try
	  			{
	  				Statement st=(Statement) con.createStatement();
	  				boolean rs=st.execute(sql);
	  				ResultSet res=st.executeQuery(sql_ch);
	  				if(res.next()) {
	  					
	  					     userID++;
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
	          		
	          	T.showShort(this, "注册成功！");
	          	}
	          	else
	          		T.showShort(this, "注册失败！");
	          	
	          	
	          	
	        	  
	          	}
	        	 
	            break;
		  }
	}

}
