package cn.zr.testfilter.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtil {
	static DataSource ds = new ComboPooledDataSource("mysqlConnection");
	public static Long starttime=0L;
	public static Long endtime=0L;
	public static Object ob = "aa";//值是任意的
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
