package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
	@Select(("SELECT * FROM CREDENTIALS WHERE userid=#{userId}"))
	List<Credentials> getCreUser(Integer userId);
	@Update("UPDATE CREDENTIALS SET url=#{url},urlusername=#{urlUserName},key=#{key},urlpassword=#{urlPassWord} WHERE credentialId=#{credentialId}")
	Integer upCre(Credentials credential);
	@Delete("DELETE FROM CREDENTIALS WHERE credentialId=#{credentialId}")
	Integer deCrel(Integer credentialId);
	@Select("Select key FROM CREDENTIALS WHERE credentialId=#{credentialId}")
	String getKey(Integer credentialId);
	@Select("Select max(credentialId) from CREDENTIALS")
	Integer getLastCre();
	@Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId} and url=#{url}")
	User getCredential(Integer userId, String url);
	@Insert("INSERT INTO CREDENTIALS (url, urlusername, key, urlpassword, userid) "+ "VALUES(#{url}, #{urlUserName}, #{key}, #{urlPassWord}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	Integer crCre(Credentials credential);
	@Select("SELECT * FROM CREDENTIALS")
	List<Credentials> getAllCre();

	

}
