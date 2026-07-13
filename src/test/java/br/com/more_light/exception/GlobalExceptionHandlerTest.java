package br.com.more_light.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Plain unit tests (no Spring context) for the error contract shared by every
 * endpoint in the project. Every resource relies on this handler to turn
 * exceptions into a consistent { "errors": [...] } payload with the right
 * HTTP status, so its behavior is worth pinning down on its own.
 */
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleApiException_returns400WithMessage() {
        ApiException ex = new ApiException("CPF invalido");

        ResponseEntity<ApiErrorResponse> response = handler.handleApiException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).containsExactly("CPF invalido");
    }

    @Test
    void handleValidationException_returns400WithFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("personDTO", "cpf", "CPF invalido");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ApiErrorResponse> response = handler.handleValidationException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).containsExactly("cpf: CPF invalido");
    }

    @Test
    void handleGenericException_returns500WithGenericMessage() {
        Exception ex = new RuntimeException("qualquer coisa inesperada");

        ResponseEntity<ApiErrorResponse> response = handler.handleGenericException(ex, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).containsExactly("Erro interno do servidor.");
    }
}
