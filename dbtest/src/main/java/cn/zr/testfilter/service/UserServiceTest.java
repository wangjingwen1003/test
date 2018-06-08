package cn.zr.testfilter.service;

import java.util.ArrayList;
import java.util.List;

import cn.zr.testfilter.pojo.User;

public class UserServiceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int j=1;j<=40;j++)
		{
			UserService userservice =new UserService();
	        List<User> list = new ArrayList<User>();
	        for(Long i=1L;i<=4000L;i++)
	        {
	        	User user = new User();
	        	user.setName("xiao-"+j+"-"+i);
	        	user.setAge((int)(i%100));
	        	list.add(user);
	        }
	        userservice.setList(list);
			userservice.start();
		}
		
	}

}
