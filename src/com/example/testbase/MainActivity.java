package com.example.testbase;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener  {

     private TextView account,password;
     private Button  GotoRegister,login; 
     private Connection con=null;
     private RadioButton is,it;
     private String queryTable="student";
     String req_pass="";
     private int usertype=1;
     public static int userID;
     public static  int appraisalForSID=5;
     //public   static final String a="asd";
   
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	 StrictMode.setThreadPolicy(policy);
        	}
        initView();
      

      
        
    }

    public void initView() {
        account = (TextView) this.findViewById(R.id.account);
        password = (TextView) this.findViewById(R.id.password);
        GotoRegister = (Button) this.findViewById(R.id.GotoRegister);
        login = (Button) this.findViewById(R.id.login);
        
        
        is.setOnClickListener(this);
        it.setOnClickListener(this);
        GotoRegister.setOnClickListener(this);
        login.setOnClickListener(this);
       
    }

   
	
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    	Intent intent;
        switch (v.getId()) {
        case R.id.GotoRegister:
        	intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            break;
        case R.id.login:
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
              
        	String sql="select * from "+queryTable+" where loginName= '"+account.getText().toString().trim()+"'";
    		System.out.println(sql);
        	try
			{
				Statement st=(Statement) con.createStatement();
				ResultSet rs=st.executeQuery(sql);
				while(rs.next())
				{
				
			    req_pass=rs.getString("password");
			     userID=rs.getInt("id");
				}
				rs.close();
				st.close();
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
        	
   
        	if(req_pass.equals(password.getText().toString().trim()))
        	{
        		if(usertype==1){
        		intent = new Intent(MainActivity.this, MainTab.class);
                startActivity(intent);	
        		}
        		else{
        			intent = new Intent(MainActivity.this, MainTab_t.class);
                    startActivity(intent);	
        		}
        	
        	}
        	else
        		T.showShort(this, "用户名或密码错误");
        	 
            break;
       
        }
    }
}
