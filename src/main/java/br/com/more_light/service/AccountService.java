package br.com.more_light.service;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.mapper.AccountMapper;
import br.com.more_light.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    @Transactional
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Transactional
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
}