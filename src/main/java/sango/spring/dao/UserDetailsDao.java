package sango.spring.dao;

import sango.spring.model.User;

public interface UserDetailsDao {
  User findUserByUsername(String username);
}
