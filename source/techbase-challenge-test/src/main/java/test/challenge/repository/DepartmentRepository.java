package test.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import test.challenge.dto.DepartmentDto;
import test.challenge.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>{
	
	List<Department> findAll();
	
	@Query("select new test.challenge.dto.DepartmentDto(dp.id, dp.name, dp.headDepartment.id, dp.headDepartment.fullName, dp.updatedAt) from Department dp left join dp.headDepartment")
    Page<DepartmentDto> findWithPagination(PageRequest pageRequest);
	
	@Query("select new test.challenge.dto.DepartmentDto(dp.id, dp.name, dp.headDepartment.id, dp.headDepartment.fullName, dp.updatedAt) from Department dp left join dp.headDepartment where dp.id = :id")
    Optional<DepartmentDto> findDeparmentById(@Param("id") String id);
	
	@Query("select new test.challenge.dto.DepartmentDto(dp.id, dp.name, dp.headDepartment.id, dp.headDepartment.fullName, dp.updatedAt) from Department dp")
    List<DepartmentDto> findDepartmentForTeam();
	
	@Query("select new test.challenge.dto.DepartmentDto(dp.id, dp.name, dp.headDepartment.id, dp.headDepartment.fullName, dp.updatedAt) from Department dp")
    List<DepartmentDto> findAllDepartment();

}
