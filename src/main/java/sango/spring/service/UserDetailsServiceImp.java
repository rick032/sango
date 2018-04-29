package sango.spring.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sango.spring.dao.UserDetailsDao;
import sango.spring.model.User;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {
	private static final Logger log = Logger.getLogger(UserDetailsServiceImp.class);

	@Autowired
	private UserDetailsDao userDetailsDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("username:" + username);
		User user = userDetailsDao.findUserByUsername(username);
		UserBuilder builder = null;
		if (user != null) {

			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.disabled(!user.isEnabled());
			builder.password(user.getPassword());
			String[] authorities = user.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new);

			builder.authorities(authorities);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		log.debug("loadUserByUsername:builded");
		return builder.build();
	}
}
