package br.com.more_light.resource;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/me")
    public ResponseEntity<AccountDTO> getMyProfile(@AuthenticationPrincipal Account loggedInUser) {
        AccountDTO response =  accountService.accountToAccountDTO(loggedInUser);
        return ResponseEntity.ok(response);
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


    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AccountDTO> deactivate(@PathVariable Long id) {
        Account account = accountService.deactivate(id);
        AccountDTO accountDTO = accountService.accountToAccountDTO(account);
        return ResponseEntity.ok(accountDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Account>> findAllPagedWithFilters(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false, name = "personName") String personName,
            @RequestParam(required = false, name = "personCpf") String personCpf,
            Pageable pageable) {
        Page<Account> accounts = accountService.findAllPagedWithFilters(email, username, active, personName, personCpf, pageable);
        return ResponseEntity.ok(accounts);
    }
}