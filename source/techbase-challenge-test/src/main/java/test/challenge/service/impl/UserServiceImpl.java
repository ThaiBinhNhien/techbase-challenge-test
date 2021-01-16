package test.challenge.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import test.challenge.entity.User;
import test.challenge.repository.UserRepository;
import test.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	
	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	
	@Override
	@Transactional
	public User changePassword(String userId, String newPassword) throws Exception{
		
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
		}
		
		return userRepository.save(user.get());
		
	}
	
}
