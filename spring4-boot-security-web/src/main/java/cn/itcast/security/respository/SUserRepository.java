package cn.itcast.security.respository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.security.domain.SUser;

/**
 * 接口操作数据库
 * 用户对象
 */
public interface SUserRepository extends JpaRepository<SUser, Serializable> {

	@Query("select u from SUser u where u.email=?1 and u.password=?2")
	SUser login(String email, String password);

	SUser findByEmailAndPassword(String email, String password);

	SUser findUserByEmail(String email);
}
