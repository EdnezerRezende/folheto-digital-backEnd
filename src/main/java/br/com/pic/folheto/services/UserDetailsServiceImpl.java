package br.com.pic.folheto.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.respositories.MembroRepository;
import br.com.pic.folheto.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MembroRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Membro membro = repo.findByEmail(email);
		if (membro == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(membro.getId(), membro.getEmail(), membro.getSenha(), membro.getPerfis());
	}
}
