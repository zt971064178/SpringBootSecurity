package cn.itcast.security.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * 角色
 */
@Entity
@Table(name="s_role", catalog="test")
public class SRole implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", nullable = false)
	private SUser SUser;
	@Column(name = "name", length = 20)
	private String name;

	public SRole() {
	}

	public SRole(SUser SUser) {
		this.SUser = SUser;
	}

	public SRole(SUser SUser, String name) {
		this.SUser = SUser;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SUser getSUser() {
		return SUser;
	}

	public void setSUser(SUser sUser) {
		SUser = sUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
