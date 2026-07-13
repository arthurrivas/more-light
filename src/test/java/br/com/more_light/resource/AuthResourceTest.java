package br.com.more_light.resource;

import br.com.more_light.dto.AccountDTO;
import br.com.more_light.security.dto.AuthRequest;
import br.com.more_light.security.dto.AuthResponse;
import br.com.more_light.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AuthResource}. AuthService is mocked and the resource is
 * invoked directly (no Spring context, no HTTP layer) since this project isn't an MVC app.
 */
@ExtendWith(MockitoExtension.class)
class AuthResourceTest {

    @Mock
    private AuthService authService;

    private AuthResource authResource;

    @BeforeEach
    void setUp() {
        authResource = new AuthResource(authService);
    }

    @Test
    void register_returnsJwtToken() {
        AccountDTO request = new AccountDTO();
        request.setUsername("arthur");
        request.setEmail("arthur@example.com");
        request.setPassword("secret");

        when(authService.register(any(AccountDTO.class)))
                .thenReturn(AuthResponse.builder().token("jwt-token").build());

        ResponseEntity<AuthResponse> response = authResource.register(request);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getToken()).isEqualTo("jwt-token");
    }

    @Test
    void authenticate_returnsJwtToken_onValidCredentials() {
        AuthRequest request = new AuthRequest("arthur@example.com", "secret");

        when(authService.authenticate(any(AuthRequest.class)))
                .thenReturn(AuthResponse.builder().token("jwt-token").build());

        ResponseEntity<AuthResponse> response = authResource.authenticate(request);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getToken()).isEqualTo("jwt-token");
    }

    @Test
    void authenticate_propagatesException_onBadCredentials() {
        AuthRequest request = new AuthRequest("arthur@example.com", "wrong-password");

        when(authService.authenticate(any(AuthRequest.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThatThrownBy(() -> authResource.authenticate(request))
                .isInstanceOf(BadCredentialsException.class);
    }
}
