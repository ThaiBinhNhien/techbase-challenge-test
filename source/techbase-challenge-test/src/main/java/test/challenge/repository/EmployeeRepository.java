package test.challenge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import test.challenge.dto.EmployeeDto;
import test.challenge.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
	
	@Query("select new test.challenge.dto.EmployeeDto(e.id, e.fullName, e.user.id, e.user.userName, e.user.role.role, e.updatedAt) from Employee e left join e.user where e.user.role.role <>'DIRECTOR'")
    Page<EmployeeDto> findWithPagination(PageRequest pageRequest);

	@Query("select new test.challenge.dto.EmployeeDto(e.id, e.fullName, e.user.id, e.user.userName, e.user.role.role, e.updatedAt) from Employee e left join e.user where e.user.role.role <>'DIRECTOR'")
    List<EmployeeDto> findAllEmployee();
	
	@Query("select new test.challenge.dto.EmployeeDto(e.id, e.fullName, e.user.id, e.user.userName, e.user.role.role, e.updatedAt) from Employee e left join e.user where e.user.role.role NOT IN ('DIRECTOR','MANAGER')")
    List<EmployeeDto> findEmployeeForDeparment();
	
	@Query("select new test.challenge.dto.EmployeeDto(e.id, e.fullName, e.user.id, e.user.userName, e.user.role.role, e.updatedAt) from Employee e left join e.user where e.user.role.role NOT IN ('DIRECTOR','MANAGER','TEAMLEADER')")
    List<EmployeeDto> findEmployeeForTeam();
	
	@Query("select new test.challenge.dto.EmployeeDto(e.id, e.fullName, e.user.id, e.user.userName, e.user.role.role, e.updatedAt) from Employee e left join e.user")
    List<EmployeeDto> findListEmployee();
	
}
