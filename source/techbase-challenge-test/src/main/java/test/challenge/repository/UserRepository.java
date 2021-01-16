package test.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.challenge.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
	
	User findByUserName(String userName);
	
}
