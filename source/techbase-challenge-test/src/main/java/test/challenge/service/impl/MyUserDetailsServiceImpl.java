package test.challenge.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import test.challenge.entity.Role;
import test.challenge.entity.User;
import test.challenge.repository.UserRepository;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    
	@Autowired
	private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
        	throw new UsernameNotFoundException("Username not found");
        }
        List<GrantedAuthority> authorities = getUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);
    }
    
    private List<GrantedAuthority> getUserAuthority(Role userRole) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(userRole.getRole()));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    	UserDetails userDetails = (UserDetails) new  org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        return userDetails;
    }
}
