package sango.spring.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sango.spring.model.User;

@Repository
public class UserDetailsDaoImp implements UserDetailsDao, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findUserByUsername(String username) {
		return sessionFactory.getCurrentSession().get(User.class, username);
	}
}
