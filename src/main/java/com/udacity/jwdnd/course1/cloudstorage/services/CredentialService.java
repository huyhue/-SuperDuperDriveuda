package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
	private CredentialsMapper credentialsMapper;
	private EncryptionService encryptionService;

	public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
		this.credentialsMapper = credentialsMapper;
		this.encryptionService = encryptionService;
	}

	public Integer crCre(Credentials credential) {
		return credentialsMapper.crCre(credential);
	}

	public Integer upCre(Credentials credential) {
		return credentialsMapper.upCre(credential);
	}

	public void updateCreKey(Credentials credential) {
		credential.setKey(credentialsMapper.getKey(credential.getCredentialId()));
	}

	public Integer deCrel(Integer credentialId) {
		return credentialsMapper.deCrel(credentialId);
	}

	public List<Credentials> getAllCre() {
		return credentialsMapper.getAllCre();
	}

	public List<Credentials> getCreUser(Integer userId) {
		return credentialsMapper.getCreUser(userId);
	}

	public void encryptPassword(Credentials credential) {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		credential.setKey(encodedKey);
		String encryptedPassword = encryptionService.encryptValue(credential.getUrlPassWord(), encodedKey);
		credential.setUrlPassWord(encryptedPassword);
		return;

	}

	public void decryptPassword(Credentials credential) {
		credential.setUrlPassWord(encryptionService.decryptValue(credential.getUrlPassWord(), credential.getKey()));
		return;
	}

	public String decryptPassword(String key, String encrptedPwd) {
		return encryptionService.decryptValue(encrptedPwd, "oks");
	}
	public String getKeyById(Integer id) {
		return credentialsMapper.getKey(id);
	}
	public Integer getLastCre() {
		return credentialsMapper.getLastCre();
	}
}
