package test.challenge.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name= "employee")
public class Employee extends AuditModel{
	
	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2", strategy = "uuid2")
	@Column(name = "id")
    private String id;
	
	@Column(name = "full_name")
	private String fullName;
	

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
    
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "headDepartment", optional = true)
	private Department department;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "teamLeader", optional = true)
	private Team team;
	
	
	@ManyToMany
	@JoinTable(
			  name = "team_join", 
			  joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"), 
			  inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
	private Set<Team> joinedTeams;

}
