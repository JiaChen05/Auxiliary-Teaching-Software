package com.example.testbase.evaluation;





import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.testbase.MainActivity;
import com.example.testbase.commom.T;
import com.example.testbase.fragment3.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class GroupEvaluation extends Fragment implements OnClickListener {
	private static final String NULL = null;
	private TextView classID1,studentID;
    private Button  ge_submit; 
    private RadioButton excellent,good,notbad,terrible;
	private Connection con=null;
	private View view;
	private float score=5;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		T.showShort(getActivity(), "SubFragment2==onCreateView");
		
		view = inflater.inflate(R.layout.groupevaluation, null);
		initView();
		return view;
	}

	private void initView() {
		   classID1 = (TextView) view.findViewById(R.id.classID1);
		   studentID = (TextView) view.findViewById(R.id.studentID);
	        ge_submit = (Button) view.findViewById(R.id.ge_submit);
	        excellent = (RadioButton) view.findViewById(R.id.excellent);
	        good = (RadioButton) view.findViewById(R.id.good);
	        notbad = (RadioButton) view.findViewById(R.id.notbad);
	        terrible = (RadioButton) view.findViewById(R.id.terrible);
	        
	        excellent.setOnClickListener(this);
	        good.setOnClickListener(this);
	        notbad.setOnClickListener(this);
	        terrible.setOnClickListener(this);
	        ge_submit.setOnClickListener(this);
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
	        case R.id.ge_submit:
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
	        	String cID_str=classID1.getText().toString().trim();
	        	String sID_str=studentID.getText().toString().trim();
	        	
	        	String sql="insert into appraisalForS "
	        			+ "values("+MainActivity.appraisalForSID+",3,"+MainActivity.userID+","
	                    +Integer.parseInt(sID_str)+","+Integer.parseInt(cID_str)+","+NULL+","+score+","+NULL+","+NULL+")";
	        	//(id,type,reviewerId,studentId,classId,number,score,remark,time)
	    		System.out.println(sql);
	    		String sql_ch="select * from appraisalForS where id="+ MainActivity.appraisalForSID;
	    		System.out.println(sql_ch);
	        	try
				{
					Statement st=(Statement) con.createStatement();
					boolean rs=st.execute(sql);
					ResultSet res=st.executeQuery(sql_ch);
					if(res.next()){
						result=true;
						MainActivity.appraisalForSID++;
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
