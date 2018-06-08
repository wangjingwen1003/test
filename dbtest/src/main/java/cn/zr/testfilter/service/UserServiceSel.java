package cn.zr.testfilter.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zr.testfilter.Utils.DataSourceUtil;
import cn.zr.testfilter.pojo.User;

public class UserServiceSel extends Thread {
    private List<User> list = new ArrayList<User>();
    @Override
    public void run() {
     Long starttime=System.currentTimeMillis();
     synchronized(DataSourceUtil.ob) {
    	if (DataSourceUtil.starttime ==0L) {
    		DataSourceUtil.starttime =starttime;
    	}
     }
	   	 for(User user:list)
	   	 {
	   			 Connection connection  = DataSourceUtil.getConnection();
		   	     String sql = "SELECT NAME,AGE FROM USERTEST where NAME='"+user.getName()+"'";
		   	     PreparedStatement pStatement = null;
		   	     ResultSet rSet = null;
	   	        try {
	   	            pStatement = (PreparedStatement) connection.prepareStatement(sql);
	   	            rSet = pStatement.executeQuery();
	   	            while (rSet.next()) {
	   	                User user2 = new User(rSet.getString("name"), rSet.getInt("age"));
		   	            System.out.println(user2.getName());
		   	            System.out.println(user2.getAge());
	   	            }
	   	        } catch (SQLException e) {
	   	            e.printStackTrace();
	   	        }finally{
	   	            try {
	   	                if (rSet != null) {
	   	                    rSet.close();
	   	                }
	   	                if (pStatement != null) {
	   	                    pStatement.close();
	   	                }
	   	                if (connection != null) {
	   	                    connection.close();
	   	                }
	   	            } catch (SQLException e) {
	   	                e.printStackTrace();
	   	            }
	   	        }
  		  
	   	 }
   	 	 Long endtime=System.currentTimeMillis();
   	 	 System.out.println(Thread.currentThread().getName() +"   "+(endtime-starttime));
   	     synchronized(DataSourceUtil.ob) {
   	    	 DataSourceUtil.endtime =endtime;
   	    	 System.out.println(Thread.currentThread().getName() +"结束，总时间   "+(endtime- DataSourceUtil.starttime));
   	     }
    }
	public List<User> getList() {
		return list;
	}
	public void setList(List<User> list) {
		this.list = list;
	}
    
    
}
