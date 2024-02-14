package adopet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/*Criando uma classe com a anotação @ControllerAdvice e métodos anotados com @ExceptionHandler para tratar diferentes
tipos de exceções.*/


//não é uma boa prática retornar a stack trace para o usuário que está consumindo nossa API, pois isso expõe detalhes
// internos da nossa aplicação. Podemos mudar essa configuração no arquivo application.properties.

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AdocaoException.class)
    public ResponseEntity<ResponseError>  adocaoException(AdocaoException ex){
        //log de mensagem de erro
        var responseError = new ResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> trataExceptionGeral(Exception ex) {
        var responseError = new ResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);

    }
}
