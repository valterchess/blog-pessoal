package br.org.generation.blogpessoal.controller;

import br.org.generation.blogpessoal.model.Postagem;
import br.org.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PostagemController {

    @Autowired
    private PostagemRepository postagemRepository;

    //retorna a lista com todos os recursos que estão no endereço /postagens
    @GetMapping
    public ResponseEntity <List<Postagem>> getAll(){
        return ResponseEntity.ok(postagemRepository.findAll());
    }
    //retorna um recurso específico indentificado pelo id
    @GetMapping("/{id}")
    public ResponseEntity <Postagem> getById(@PathVariable long id){
        return postagemRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //retorna todos os recursos que contem um ou mais chars informados pelo cliente
    @GetMapping("/titulos/{titulo}")
    public ResponseEntity <List<Postagem>> getAllByTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    //insere um novo recurso
    @PostMapping
    public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
    }

    //atualiza um recurso existente, caso o recurso não exista retorna um notFound
    @PutMapping
	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem){
        return postagemRepository.findById(postagem.getId())
                .map(resposta -> ResponseEntity.ok(postagemRepository.save(postagem)))
                .orElse(ResponseEntity.notFound().build());
	}
    
    //deleta um recurso existente, caso o recurso não exista retorna um notFound
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletaPostagem(@PathVariable long id) {
        if (!postagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}