package br.org.generation.blogpessoal.repository;

import br.org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){
        usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278"));

        usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278"));

        usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278"));
    }

    @Test
    @DisplayName("Retorna 1 Usuario")
    public void DeveRetornarUmUsuario(){
        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
        assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
        //assertEquals("joao@email.com.br", usuario.get().getUsuario());
    }
    @Test
    @DisplayName("Retorna 3 Usuarios")
    public void DeveRetornarTresUsuarios(){
        List<Usuario> ListUsuario = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, ListUsuario.size());
        assertTrue(ListUsuario.get(0).getNome().equals("João da Silva"));
        assertTrue(ListUsuario.get(0).getNome().equals("Manuela da Silva"));
        assertTrue(ListUsuario.get(0).getNome().equals("Adriana da Silva"));
    }
}
