package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class HashService {
	public String getHashedValue(String data, String salt) {
		byte[] str = null;
		KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), 5000, 128);
		try {
			SecretKeyFactory to = SecretKeyFactory.getInstance("huyhuecndsf");
			str = to.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
		}
		return Base64.getEncoder().encodeToString(str);
	}
}
