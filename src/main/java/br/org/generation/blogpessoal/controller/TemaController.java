package br.org.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.org.generation.blogpessoal.model.Tema;
import br.org.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class TemaController {

    @Autowired
    TemaRepository temaRepository;

    @GetMapping
    public ResponseEntity<List<Tema>> getAll(){
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable long id) {
        return temaRepository.findById(id)
        .map(resp -> ResponseEntity.ok(resp))
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/descricoes/{descricao}")
    public ResponseEntity<List<Tema>> getAllByDescricao(@PathVariable String descricao){
       return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
    }

    @PostMapping
    public ResponseEntity<Tema> postTema(@RequestBody Tema tema){
        return ResponseEntity.ok(temaRepository.save(tema));
    }

    @PutMapping
    public ResponseEntity<Tema> putTema(@RequestBody Tema tema) {
        return temaRepository.findById(tema.getId())
        .map(resposta -> ResponseEntity.ok(temaRepository.save(tema)))
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return temaRepository.findById(id)
        .map(verificacao -> {
            temaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
