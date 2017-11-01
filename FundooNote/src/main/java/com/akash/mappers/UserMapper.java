package com.akash.mappers;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.akash.model.User;

public interface UserMapper {

	@Insert("INSERT INTO userdetails(user_name,user_email," + "user_address,user_password) VALUES" + "(#{user_name},"
			+ "#{user_email}, #{user_address},#{user_password})")

	public int insertUser(User user);

	@Insert("INSERT INTO shareinfo(user_id,id,share_user_id) VALUES(#{0},#{1},#{2})")

	public void insertShareNote(int user_id, int note_id, int shareuser_id);

	@Select("SELECT * FROM userdetails WHERE  user_email = #{ user_email}")
	public User getUserByUserEmail(String user_email);

	@Select("SELECT * FROM userdetails WHERE user_id=#{user_id}")
	public boolean getUserById(int user_id);

	@Update("update userdetails set resetToken=#{resetToken} where user_email = #{ user_email} ")
	public int insertToken(User user);

	@Select("select * from userdetails where resetToken=#{resetToken} ")
	public User findUserByResetToken(String resetToken);

	@Update("update userdetails set user_password=#{user_password} where resetToken = #{resetToken} ")
	public void updatePassword(User user);

}
