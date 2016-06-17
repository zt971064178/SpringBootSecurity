package cn.itcast.security.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.itcast.security.domain.SRole;
import cn.itcast.security.domain.SUser;

/**
 * 权限判断
 */
public class SecurityUser extends SUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	public SecurityUser(SUser suser) {
		if(suser != null) {
			this.setId(suser.getId());
			this.setName(suser.getName());
			this.setEmail(suser.getEmail());
			this.setPassword(suser.getPassword());
			this.setDob(suser.getDob());
			this.setSRoles(suser.getSRoles());
		}		
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<SRole> userRoles = this.getSRoles() ;
		
		if(userRoles != null) {
			for (SRole role : userRoles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
				authorities.add(authority);
			}
		}
		
		return authorities;
	}

	// 登陆用户名获取邮箱号
	@Override
	public String getUsername() {
		return super.getEmail();
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true ;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true ;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true ;
	}

	@Override
	public boolean isEnabled() {
		return true ;
	}
}
