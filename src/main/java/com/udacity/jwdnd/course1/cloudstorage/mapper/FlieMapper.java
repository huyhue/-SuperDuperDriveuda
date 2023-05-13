package com.udacity.jwdnd.course1.cloudstorage.mapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Flies;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface FlieMapper {
	@Select("SELECT filename FROM FILES WHERE userid=#{userId}")
	List<String> getFUser(Integer userId);
	@Select("SELECT * FROM FILES WHERE userid=#{userId}")
	List<Flies> getallF(Integer userId);
	@Delete("DELETE FROM FILES WHERE filename=#{fileName}")
	Integer deFile(String fileName);
	@Select("SELECT * FROM FILES WHERE filename=#{fileName} and userid=#{userId} ")
	Flies getFfUser(Integer userId, String fileName);
	@Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata) VALUES(#{fileName},#{contentType},#{fileSize},#{userId},#{fileData})")
	@Options(useGeneratedKeys = true, keyProperty = "key")
	Integer inFile(Flies flies);
}
