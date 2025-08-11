package br.com.more_light.service;

import br.com.more_light.domain.Account;
import br.com.more_light.domain.Person;
import br.com.more_light.domain.Role;
import br.com.more_light.mapper.AccountMapper;
import br.com.more_light.mapper.PersonMapper;
import br.com.more_light.repository.AccountRepository;
import br.com.more_light.repository.PersonRepository;
import br.com.more_light.repository.RoleRepository;
import br.com.more_light.security.dto.AuthRequest;
import br.com.more_light.security.dto.AuthResponse;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountMapper accountMapper;
    private final PersonMapper personMapper;

    public AuthResponse register(AccountDTO request) {
        // Busca a role "ROLE_USER" no banco.
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Erro: Role 'ROLE_USER' não encontrada."));

        Person person = personMapper.personDtoToPerson(request.getPerson());
        personRepository.save(person);

        Account account = accountMapper.accountDtoToAccount(request);
        account.setActive(true);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        account.setPerson(person);
        accountRepository.save(account);

        String jwtToken = jwtService.generateToken(account);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        // O AuthenticationManager verifica se o email e senha batem. Se não, ele lança uma exceção.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Se a autenticação foi bem-sucedida, buscamos o usuário e geramos um token.
        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(account);
        return AuthResponse.builder().token(jwtToken).build();
    }
}