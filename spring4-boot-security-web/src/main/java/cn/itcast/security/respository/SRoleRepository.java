package cn.itcast.security.respository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.security.domain.SRole;

/**
 * 接口操作数据库
 * 角色对象
 */
public interface SRoleRepository extends JpaRepository<SRole, Serializable> {

}
