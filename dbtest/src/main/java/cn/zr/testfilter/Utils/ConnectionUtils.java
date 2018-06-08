package cn.zr.testfilter.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.zr.testfilter.pojo.User;

public class ConnectionUtils {
	static DataSource ds = new ComboPooledDataSource("mysqlConnection");
    public static void main(String[] args) throws Exception{
    	 Long starttime=System.currentTimeMillis();
         ConnectionUtils utils = new ConnectionUtils();
         utils.testinsert();
    	 Long endtime=System.currentTimeMillis();
    	 System.out.println(endtime-starttime);
    }
    public void testinsert() throws Exception{
        List<User> list = new ArrayList<User>();
        for(Long i=4001L;i<=6000L;i++)
        {
        	User user = new User();
        	user.setName("xiao"+i);
        	user.setAge((int)(i%100));
        	list.add(user);
        }
        insertData(list);
    }
    
    public void insertData(List<User> list) throws Exception{
    	 for(User user:list)
    	 {
	    	 Connection connection  = ConnectionUtils.getConnection();
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
    	 
    	 }
    	 
    }
    public List<User> getData(){
        List<User> list = new ArrayList<User>();
        Connection connection  = ConnectionUtils.getConnection();
        String sql = "SELECT NAME,AGE FROM USERTEST";
        PreparedStatement pStatement = null;
        ResultSet rSet = null;
        try {
            pStatement = (PreparedStatement) connection.prepareStatement(sql);
            rSet = pStatement.executeQuery();
            while (rSet.next()) {
                User user = new User(rSet.getString("name"), rSet.getInt("age"));
                list.add(user);
            }
            return list;
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
        return null;
    }
    
    
    public static Connection getConnection() {
        try {
            Connection connection = ds.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}