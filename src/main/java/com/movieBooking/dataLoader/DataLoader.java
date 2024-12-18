package com.movieBooking.dataLoader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.movieBooking.model.Role;
import com.movieBooking.repository.RoleRepository;

@Component
public class DataLoader  implements CommandLineRunner{
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		List<Role> roles=roleRepository.findAll();
		
		if(roles.isEmpty()) {
			
			roleRepository.save(new Role("ROLE_ADMIN"));
			roleRepository.save(new Role("ROLE_THOWNER"));
			roleRepository.save(new Role("ROLE_USER"));
		}
	}
		
	
}
