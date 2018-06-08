package cn.zr.testfilter.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zr.testfilter.Utils.DataSourceUtil;
import cn.zr.testfilter.pojo.User;

public class UserService extends Thread {
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
   		 try {
   	
   			 Connection connection  = DataSourceUtil.getConnection();
	    	 connection.setAutoCommit(false);//设置手动提交
	    	 PreparedStatement pStatement = null;
	    	 try {
	    		 pStatement= connection.prepareStatement("INSERT INTO USERTEST(NAME,age) VALUES(?,?)");
	    		 pStatement.setString(1, user.getName());
	    		 pStatement.setInt(2,user.getAge());
	    		 pStatement.addBatch();
	    		 pStatement.executeBatch();
	    		 connection.commit();
	    		 pStatement.clearBatch();
	    	 } catch (SQLException e) {
	             e.printStackTrace();
	             connection.rollback();  
	         }finally{
	             try {
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
	    	 } catch (Exception e) {
	       		 e.printStackTrace();
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
