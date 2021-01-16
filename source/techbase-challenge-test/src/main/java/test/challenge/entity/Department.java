package test.challenge.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name= "department")
public class Department  extends AuditModel {
	
	@Id
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2", strategy = "uuid2")
	@Column(name = "id")
    private String id;
	
	@Column(name = "department_name")
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "head_department_id", referencedColumnName = "id", nullable = false)
	private Employee headDepartment;
	
	@OneToMany(mappedBy = "department")
	private Set<Team> teams;

}
