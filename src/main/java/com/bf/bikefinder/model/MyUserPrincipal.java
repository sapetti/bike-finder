package com.bf.bikefinder.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserPrincipal implements UserDetails {

	String ADMIN_ROLE = "ADMIN";
	
	private static final long serialVersionUID = 1L;
	
	private User user;

	public MyUserPrincipal(User user) {
		this.setUser(user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		if (user.isIsadmin()) {
			list.add(new SimpleGrantedAuthority(ADMIN_ROLE));
		}

        return list;
	}
	
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
