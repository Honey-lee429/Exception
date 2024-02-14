package adopet.api.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/* log de mensagem de erro */
public record ResponseError(String message, HttpStatus badRequest, LocalDateTime time) {
}
