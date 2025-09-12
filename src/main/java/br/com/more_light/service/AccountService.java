package br.com.more_light.service;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.mapper.AccountMapper;
import br.com.more_light.repository.AccountRepository;
import br.com.more_light.repository.specification.AccountSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Account update(Long id, Account account) {
        account.setId(id);
        return accountRepository.save(account);
    }


    public void delete(Long id) {
        Account account = findById(id);
        accountRepository.delete(account);
    }

    public Account accountDTOToAccount(AccountDTO accountDTO) {
        return accountMapper.accountDtoToAccount(accountDTO);
    }

    public AccountDTO accountToAccountDTO(Account account) {
        return accountMapper.accountToAccountDTO(account);
    }

    public Page<Account> findAllPagedWithFilters(String email, String username, Boolean active, String personName, String personCpf, Pageable pageable) {
        Specification<Account> spec = Specification.where(AccountSpecification.emailContains(email))
                .and(AccountSpecification.usernameContains(username))
                .and(AccountSpecification.isActive(active))
                .and(AccountSpecification.personNameContains(personName))
                .and(AccountSpecification.personCpfContains(personCpf));
        return accountRepository.findAll(spec, pageable);
    }
}