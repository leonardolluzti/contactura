package contactura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import contactura.model.ContacturaUser;
import contactura.repository.ContacturaUserRepository;

public class CustomUserDetailService implements UserDetailsService{
	
	private final ContacturaUserRepository contacturaUserRepository;
	
	@Autowired
	public CustomUserDetailService(ContacturaUserRepository contacturaUserRepository) {
		this.contacturaUserRepository = contacturaUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ContacturaUser user = Optional.ofNullable(contacturaUserRepository.findByUsername(username))
				.orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		return new org.springframework.security.core.userdetails.User
				(user.getUsername(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
	}
	

}
