package dao;

import java.util.List;

import entities.User;

public interface UserDao {
	void insert(User user);
	void update(User user, String nip);
	void delete(String nip);
	List<User> get(String key);
	boolean IsAvalilable(String nip, String username);
	User findMyAccount(String username);
	void updateMyAccount(User user, String username);
}
