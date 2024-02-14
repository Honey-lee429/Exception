package adopet.api.controller;

import adopet.api.dto.AdocaoDTO;
import adopet.api.dto.AprovarAdocaoDTO;
import adopet.api.dto.ReprovarAdocaoDTO;
import adopet.api.dto.SolicitacaoDeAdocaoDTO;
import adopet.api.exception.AdocaoException;
import adopet.api.model.Adocao;
import adopet.api.service.AdocaoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("adocao")
public class AdocaoController {

    @Autowired
    private AdocaoService service;

    @GetMapping
    public ResponseEntity<List<AdocaoDTO>> buscarTodos(){
        List<AdocaoDTO> adocoes = service.listarTodos();
        return ResponseEntity.ok(adocoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdocaoDTO> buscar(@PathVariable Long id){
        AdocaoDTO adocao = service.listar(id);
        return ResponseEntity.ok(adocao);
    }

    /**
     * <p>diferença entre Checked Exceptions e Unchecked #Exceptions
     * Quando as checked Exceptions devem ser usadas? Use checked Exceptions quando houver um erro
     * recuperável ou um requisito de negócio importante. A exceção verificada mais comum é a classe Exception.
     * Duas classes relacionadas da Exception são FileNotFoundException e SQLException.
     * Você é obrigado a manipular ou declarar essas exceções. Você deve lançar ou capturar a exceção,
     * caso contrário o código não será compilado. Quando as unchecked Exceptions devem ser usadas?
     * Use unchecked Exceptions quando não houver recuperação. Por exemplo, quando a memória do servidor
     * é usada em excesso. RuntimeException é usado para erros quando seu aplicativo não pode recuperar.
     * Por exemplo, NullPointerException e ArrayOutOfBoundsException. Você pode evitar uma RuntimeException
     * com um comando  'if'. Você não deve lidar com isso ou capturar a Exception. Há também a classe Error.
     * Também é uma exceção não verificada. Nunca tente capturar ou manipular esse tipo de exceção. Eles são
     * erros da JVM e são o tipo mais grave de exceção em Java. Você deve analisar a causa de exceções como
     * essa e corrigir seu código.</p>
     */

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitacaoDeAdocaoDTO dados){

        this.service.solicitar(dados);

// Criada a classe GlobalExceptionHandler para lidar de forma global as exceções que utilizam AdocaoException
//        try {
//            this.service.solicitar(dados);
//            // trabalhando com multi exception
//        }catch (AdocaoException | UnsupportedOperationException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//
//        }
        return ResponseEntity.ok("Adoção solicitada com sucesso!");
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovarAdocaoDTO dto){
        this.service.aprovar(dto);

// exception tratada com o metodo trataExceptionGeral() da classe GlobalExceptionHandler
//         try {
//         this.service.aprovar(dto);
//        }catch (EntityNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("adocao nao encontrada");
//           // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); mensagem padrão
//        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Adocao> aprova(@RequestBody @Valid AprovarAdocaoDTO dto) {
        return service.aprovardto(dto).map(aprovado -> ResponseEntity.ok().body(aprovado))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovarAdocaoDTO dto){
        this.service.reprovar(dto);
        return ResponseEntity.ok().build();
    }
}
