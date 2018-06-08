package cn.zr.testfilter.service;

import java.util.ArrayList;
import java.util.List;

import cn.zr.testfilter.pojo.User;

public class UserServiceSelTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int j=20;j<=80;j++)
		{
			UserServiceSel userservice =new UserServiceSel();
	        List<User> list = new ArrayList<User>();
	        for(Long i=1L;i<=5000L;i++)
	        {
	        	User user = new User();
	        	user.setName("xiao-"+j+"-"+i);
	        	list.add(user);
	        }
	        userservice.setList(list);
			userservice.start();
		}
		
	}

}
