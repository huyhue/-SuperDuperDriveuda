package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
	@Select("Select * from NOTES WHERE userid = #{userid}")
	List<Notes> getNoU(Integer userid);
	@Update("UPDATE NOTES SET notetitle=#{noteTitle},notedescription=#{noteDescription},userid=#{userid} WHERE noteid=#{noteid}")
	Integer upNote(Notes notes);
	@Delete("DELETE FROM NOTES WHERE noteid=#{noteid}")
	Integer deNote(Integer noteid);
	@Select("Select max(noteid) from NOTES")
	Integer getLaNo();
	@Insert("INSERT INTO NOTES(notetitle,notedescription,userid)  VALUES(#{noteTitle},#{noteDescription},#{userid})")
	@Options(useGeneratedKeys = true, keyProperty = "noteid")
	Integer inNote(Notes notes);
}
