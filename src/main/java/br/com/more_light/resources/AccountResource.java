package br.com.more_light.resources;

import br.com.more_light.domain.Account;
import br.com.more_light.domain.Person;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.service.AccountService;
import br.com.more_light.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountResource {

    private final AccountService accountService;
    private final PersonService personService;

    public AccountResource(AccountService accountService, PersonService personService) {
        this.accountService = accountService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        Account newAccount = accountService.accountDTOToAccount(accountDTO);
        newAccount = accountService.create(newAccount);
        accountDTO = accountService.accountToAccountDTO(newAccount);
        return ResponseEntity.status(201).body(accountDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        Account account = accountService.accountDTOToAccount(accountDTO);
        account = accountService.update(id, account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}