package com.chenxing.managesystem.service.impl;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenxing.managesystem.domain.User;
import com.chenxing.managesystem.mapper.UserMapper;
import com.chenxing.managesystem.service.UserService;
import com.github.pagehelper.PageHelper;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;// 这里会报错，但是并不会影响

	@Override
	public int addUser(User user) {

		return userMapper.insertSelective(user);
	}
	/*
	 * 这个方法中用到了我们开头配置依赖的分页插件pagehelper 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
	 * pageNum 开始页数 pageSize 每页显示的数据条数
	 */
	@Override
	public List<User> findAllUser(int pageNum, int pageSize, String userDetail) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
		PageHelper.startPage(pageNum, pageSize); 

		Clob clob = null;
		try {
			clob = new javax.sql.rowset.serial.SerialClob(userDetail.toCharArray());
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userMapper.selectAllUser(clob);
	}
		
}
