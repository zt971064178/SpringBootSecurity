package cn.itcast.security.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 用户
 */
@Entity
@Table(name="s_user", catalog="test")
public class SUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "name", length = 20)
	private String name;
	@Column(name = "email", length = 20)
	private String email;
	@Column(name = "password", length = 20)
	private String password;
	@Temporal(TemporalType.DATE)
	@Column(name = "dob", length = 10)
	private Date dob;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "SUser")
	private Set<SRole> SRoles = new HashSet<SRole>(0);
	
	public SUser() {

	}

	public SUser(String name, String email, String password, Date dob, Set<SRole> SRoles) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.SRoles = SRoles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Set<SRole> getSRoles() {
		return SRoles;
	}

	public void setSRoles(Set<SRole> sRoles) {
		SRoles = sRoles;
	}
}
