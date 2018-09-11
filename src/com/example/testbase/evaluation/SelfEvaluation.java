package com.example.testbase.evaluation;





import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.testbase.MainActivity;
import com.example.testbase.MainTab;
import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RadioButton;

public class SelfEvaluation extends Fragment implements OnClickListener{
    
	private static final String NULL = null;
	private TextView classID;
    private Button  se_submit; 
    private RadioButton excellent,good,notbad,terrible;
	private Connection con=null;
	private View view;
	private float score=5;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.selfevaluation, null);
		initView();
		T.showShort(getActivity(), "SubFragment1==onCreateView");
		
		return view;
	}
	private void initView() {
        classID = (TextView) view.findViewById(R.id.classID);
        se_submit = (Button) view.findViewById(R.id.se_submit);
        excellent = (RadioButton) view.findViewById(R.id.excellent);
        good = (RadioButton) view.findViewById(R.id.good);
        notbad = (RadioButton) view.findViewById(R.id.notbad);
        terrible = (RadioButton) view.findViewById(R.id.terrible);
        
        excellent.setOnClickListener(this);
        good.setOnClickListener(this);
        notbad.setOnClickListener(this);
        terrible.setOnClickListener(this);
        se_submit.setOnClickListener(this);
       
       
    }
	@Override
	public void onClick(View v) {
        switch (v.getId()) {
        case R.id.excellent:
        	score=5;
        	 break;
        case R.id.good:
        	score=4;
        	 break;
        case R.id.notbad:
        	score=3;
        	 break;
        case R.id.terrible:
        	score=2;
        	 break;
        case R.id.se_submit:
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
        	String cID_str=classID.getText().toString().trim();
        	String sql="insert into appraisalForS "
        			+ "values("+MainActivity.appraisalForSID+",3,"+MainActivity.userID+","
                    +MainActivity.userID+","+Integer.parseInt(cID_str)+","+NULL+","+score+","+NULL+","+NULL+")";
        	//(id,type,reviewerId,studentId,classId,number,score,remark,time)
    		System.out.println(sql);
    		String sql_ch="select * from appraisalForS where id="+ MainActivity.appraisalForSID;
    		System.out.println(sql_ch);
        	try
			{
				Statement st=(Statement) con.createStatement();
				boolean rs=st.execute(sql);
				ResultSet res=st.executeQuery(sql_ch);
				if(res.next()) {
					if(res.getInt("type")==3){
						MainActivity.appraisalForSID++;
						result=true;	
					}
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
        		
        	T.showShort(getActivity(), "评价成功！");
        	}
        	else
        		T.showShort(getActivity(), "评价失败！");
            break;
        	 
        }
        	 
		
	}
}
