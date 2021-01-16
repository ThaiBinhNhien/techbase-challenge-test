package test.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.challenge.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
	Role findByRole(String role);

}
