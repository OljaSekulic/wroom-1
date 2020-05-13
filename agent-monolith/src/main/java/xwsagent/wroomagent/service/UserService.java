package xwsagent.wroomagent.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xwsagent.wroomagent.domain.Privilege;
import xwsagent.wroomagent.domain.Role;
import xwsagent.wroomagent.domain.User;
import xwsagent.wroomagent.dto.SignupRequestDTO;
import xwsagent.wroomagent.repository.RoleRepository;
import xwsagent.wroomagent.repository.SignupRequestRepository;
import xwsagent.wroomagent.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SignupRequestRepository signupRequestRepository;

//	Good for now
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
					getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));

	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	/*
	 * Saves a signup request in DB and returns it.
	 */
	public SignupRequestDTO signup(SignupRequestDTO requestDTO) {
		SignupRequestDTO ret = new SignupRequestDTO();
		
		User user = this.findByEmail(requestDTO.getEmail());
		if(user != null) {
			return null;
		}
		
//		TODO: CONVERTER
		
		return ret;
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
}
