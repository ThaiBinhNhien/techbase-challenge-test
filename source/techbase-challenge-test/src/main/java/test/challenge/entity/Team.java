package test.challenge.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class Team extends AuditModel{
	
	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2", strategy = "uuid2")
	@Column(name = "id")
    private String id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="department_id", referencedColumnName = "id", nullable=false)
	private Department department;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_leader_id", referencedColumnName = "id", nullable = false)
	private Employee teamLeader;
	
	@ManyToMany(mappedBy = "joinedTeams")
	private Set<Employee> teamMember;
	
	
}
