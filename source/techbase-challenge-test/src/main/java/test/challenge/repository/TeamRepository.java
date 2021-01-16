package test.challenge.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import test.challenge.dto.TeamDto;
import test.challenge.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String>{
	
	@Query("select new test.challenge.dto.TeamDto(t.id, t.name, t.department.id, t.department.name, t.teamLeader.id, t.teamLeader.fullName, t.updatedAt) from Team t left join t.department")
    Page<TeamDto> findWithPagination(PageRequest pageRequest);
	
	@Query("select new test.challenge.dto.TeamDto(t.id, t.name, t.department.id, t.department.name, t.teamLeader.id, t.teamLeader.fullName, t.updatedAt) from Team t left join t.department")
    List<TeamDto> findAllTeam();

}
