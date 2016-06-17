package cn.itcast.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.security.domain.SUser;
import cn.itcast.security.respository.SUserRepository;

/**
 * 业务逻辑
 */
@Service
public class SUserService {
	@Autowired
	private SUserRepository suserRepository;

	public List<SUser> findAll() {
		return suserRepository.findAll() ;
	}
	
	public SUser create(SUser user) {
		return suserRepository.save(user) ;
	}
	
	public SUser findUserById(int id) {
		return suserRepository.findOne(id);
	}

	public SUser login(String email, String password) {
		return suserRepository.findByEmailAndPassword(email, password);
	}

	public SUser update(SUser user) {
		return suserRepository.save(user);
	}

	public void deleteUser(int id) {
		suserRepository.delete(id);
	}

	public SUser findUserByEmail(String email) {
		return suserRepository.findUserByEmail(email);
	}
}
