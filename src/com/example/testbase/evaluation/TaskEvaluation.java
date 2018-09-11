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

public class TaskEvaluation extends Fragment implements OnClickListener {
	private static final String NULL = null;
	private TextView taskID;
    private Button  te_submit; 
    private RadioButton score1,score2,score3,score4,score5;
	private Connection con=null;
	private View view;
	private float score=1;
	public static  int appraisalForTID=4;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		T.showShort(getActivity(), "SubFragment3==onCreateView");
		
		view = inflater.inflate(R.layout.taskevaluation, null);
		initView();
		
		
		return view;
	}
	private void initView() {
		    taskID= (TextView) view.findViewById(R.id.taskID);
	        te_submit = (Button) view.findViewById(R.id.te_submit);
	        score1 = (RadioButton) view.findViewById(R.id.score1);
	        score2 = (RadioButton) view.findViewById(R.id.score2);
	        score3 = (RadioButton) view.findViewById(R.id.score3);
	        score4 = (RadioButton) view.findViewById(R.id.score4);
	        score5 = (RadioButton) view.findViewById(R.id.score5);
	        
	        
	        te_submit.setOnClickListener(this);
	        score1.setOnClickListener(this);
	        score2.setOnClickListener(this);
	        score3.setOnClickListener(this);
		    score4.setOnClickListener(this);
		    score5.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
	        case R.id.score1:
	        	score=1;
	        	 break;
	        case R.id.score2:
	        	score=2;
	        	 break;
	        case R.id.score3:
	        	score=3;
	        	 break;
	        case R.id.score4:
	        	score=4;
	        	 break;
	        case R.id.score5:
	        	score=5;
	        	 break;
	        case R.id.te_submit:
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
	        	String tID_str=taskID.getText().toString().trim();
	        	String sql="insert into appraisalForT "
	        			+ "values("+appraisalForTID+","+MainActivity.userID+","
	                    +Integer.parseInt(tID_str)+","+score+","+NULL+","+NULL+")";
	        	//(id,type,reviewerId,studentId,classId,number,score,remark,time)
	    		System.out.println(sql);
	    		String sql_ch="select * from appraisalForT where id="+ appraisalForTID;
	    		System.out.println(sql_ch);
	        	try
				{
					Statement st=(Statement) con.createStatement();
					boolean rs=st.execute(sql);
					ResultSet res=st.executeQuery(sql_ch);
					if(res.next()) {
					
							MainActivity.appraisalForSID++;
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
	        		appraisalForTID++;
	        	T.showShort(getActivity(), "评价成功！");
	        	}
	        	else
	        		T.showShort(getActivity(), "评价失败！");
	            break;
	        	 
	        }
		
	}
}
