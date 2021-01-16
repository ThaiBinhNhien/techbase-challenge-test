package test.challenge.service;

import test.challenge.entity.User;

public interface UserService {
	
	User findUserByUserName(String userName);
	
	User changePassword(String userId, String newPassword) throws Exception;
	
}
