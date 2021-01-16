package test.challenge.service;

import java.util.List;

import test.challenge.dto.TeamDto;
import test.challenge.dto.TeamEmployeeDto;
import test.challenge.request.TeamRequest;
import test.challenge.response.PagingResponse;
import test.challenge.response.SingleResponse;

public interface TeamService {
	
	PagingResponse<List<TeamDto>> getAllTeam(int page, int record, String search);
	
	SingleResponse<List<TeamDto>> getAllTeam();
	
	SingleResponse<TeamDto> getDetail(String id);
	
	SingleResponse<List<TeamEmployeeDto>> getListTeamByEmployee(String id);
	
	SingleResponse<String> createTeam(TeamRequest teamRequest) throws Exception;
	
	SingleResponse<String> addEmployeeTeam(List<String> ids, String teamid) throws Exception;
	
	SingleResponse<String> updateTeam(TeamRequest teamRequest) throws Exception;
	
	SingleResponse<String> removeTeam(String id) throws Exception;

}
