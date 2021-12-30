package dao;

import entities.User;

public interface AuthDao {
	User auth(String username, String password);
	void changePassword(String username, String password);
	void initReport();
}
