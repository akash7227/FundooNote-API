package com.akash.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.akash.model.Note;

public interface NoteMapper {

	@Insert("insert into fundoonote(user_id,title,description,date) values(#{user.user_id},#{title},#{description},#{date})")
	public void insertnote(Note note);

	@Select("select * from fundoonote where id IN(select id from shareinfo where share_user_id=#{user.user_id}) union select * from fundoonote where user_id =#{user.user_id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "user", column = "user_id"),
			@Result(property = "title", column = "title"), @Result(property = "description", column = "description"),
			@Result(property = "date", column = "date")

	})
	public List<Note> selectById(Note note);

	@Select("select * from fundoonote where id=#{id}")
	public Note selectNoteByNoteId(Note note);

	@Select("select * from fundoonote")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "user.user_id", column = "user_id"),
			@Result(property = "title", column = "title"), @Result(property = "description", column = "description"),
			@Result(property = "date", column = "date") })
	public List<Note> selectAllNote();

	@Update("update fundoonote set title=#{title},description=#{description},date=#{date} where id=#{id}")
	public void update(Note note);

	@Update("update fundoonote set archive=#{archive} where id=#{id}")
	public void archive(Note note);

	@Update("update fundoonote set trash=#{trash} where id=#{id}")
	public void trash(Note note);

	@Update("update fundoonote set pin=#{pin} where id=#{id}")
	public void pin(Note note);

	@Delete("delete from fundoonote where id=#{id}")
	public int deleteForeverByNoteId(Note note);

}
