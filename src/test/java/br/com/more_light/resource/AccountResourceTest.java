package br.com.more_light.resource;

import br.com.more_light.domain.Account;
import br.com.more_light.dto.AccountDTO;
import br.com.more_light.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AccountResource}. AccountService is mocked and the resource is
 * invoked directly (no Spring context, no HTTP layer) since this project isn't an MVC app.
 */
@ExtendWith(MockitoExtension.class)
class AccountResourceTest {

    @Mock
    private AccountService accountService;

    private AccountResource accountResource;

    private Account account;
    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountResource = new AccountResource(accountService);

        account = new Account();
        account.setId(1L);
        account.setUsername("arthur");
        account.setEmail("arthur@example.com");
        account.setPassword("secret");

        accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setUsername("arthur");
        accountDTO.setEmail("arthur@example.com");
        accountDTO.setPassword("secret");
    }

    @Test
    void getMyProfile_returnsAuthenticatedAccountProfile() {
        when(accountService.accountToAccountDTO(account)).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> response = accountResource.getMyProfile(account);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getEmail()).isEqualTo("arthur@example.com");
    }

    @Test
    void findById_returnsAccount_whenExists() {
        when(accountService.findById(1L)).thenReturn(account);

        ResponseEntity<Account> response = accountResource.findById(1L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getUsername()).isEqualTo("arthur");
    }

    @Test
    void findById_propagatesException_whenAccountNotFound() {
        when(accountService.findById(99L)).thenThrow(new RuntimeException("Account not found with id: 99"));

        assertThatThrownBy(() -> accountResource.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Account not found with id: 99");
    }

    @Test
    void create_returns201WithCreatedAccount() {
        when(accountService.accountDTOToAccount(any(AccountDTO.class))).thenReturn(account);
        when(accountService.create(any(Account.class))).thenReturn(account);
        when(accountService.accountToAccountDTO(any(Account.class))).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> response = accountResource.create(accountDTO);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody().getUsername()).isEqualTo("arthur");
    }

    @Test
    void update_returnsUpdatedAccount() {
        when(accountService.accountDTOToAccount(any(AccountDTO.class))).thenReturn(account);
        when(accountService.update(eq(1L), any(Account.class))).thenReturn(account);

        ResponseEntity<Account> response = accountResource.update(1L, accountDTO);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getUsername()).isEqualTo("arthur");
    }

    @Test
    void delete_returns204NoContent() {
        ResponseEntity<Void> response = accountResource.delete(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(accountService).delete(1L);
    }

    @Test
    void findAllPagedWithFilters_returnsPageOfAccounts() {
        Page<Account> page = new PageImpl<>(List.of(account), PageRequest.of(0, 10), 1);
        when(accountService.findAllPagedWithFilters(any(), any(), any(), any(), any(), any())).thenReturn(page);

        ResponseEntity<Page<Account>> response =
                accountResource.findAllPagedWithFilters("arthur", null, null, null, null, PageRequest.of(0, 10));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getTotalElements()).isEqualTo(1);
        assertThat(response.getBody().getContent().get(0).getUsername()).isEqualTo("arthur");
    }
}
