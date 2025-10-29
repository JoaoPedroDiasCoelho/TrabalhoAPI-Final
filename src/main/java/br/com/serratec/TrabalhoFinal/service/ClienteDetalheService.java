package br.com.serratec.TrabalhoFinal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.serratec.TrabalhoFinal.entity.Cliente;
import br.com.serratec.TrabalhoFinal.repository.IClienteRepository;

//Clase que valida o login
@Service
public class ClienteDetalheService implements UserDetailsService {
	
	@Autowired
	private IClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional <Cliente> cliente = repository.findByEmail(username);
		if(cliente.isPresent()) {
			return cliente.get();
		}
		throw new UsernameNotFoundException("Email nao encontrado!");
	}
}
