package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
	private UsersMapper userM;
	private HashService hashS;

	public AuthenticationService(UsersMapper userM, HashService hashS) {
		this.userM = userM;
		this.hashS = hashS;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String us = auth.getName();
		String plainPassword = auth.getCredentials().toString();
		User user = userM.getUser(us);
		if (user != null) {
			String koo = hashS.getHashedValue(plainPassword, user.getSalt());
			if (user.getPassWord().equals(koo)) {
				return new UsernamePasswordAuthenticationToken(us, koo, new ArrayList<>());
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals("dequsa");
	}

	public String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
