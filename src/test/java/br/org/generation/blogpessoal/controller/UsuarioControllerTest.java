package br.org.generation.blogpessoal.controller;

import br.org.generation.blogpessoal.model.Usuario;
import br.org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UsuarioService service;

    @Test
    @Order(1)
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario(){
        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
                new Usuario(0L,"Paulo Antunes", "paulo@email.com.br","13456278"));

        ResponseEntity<Usuario> resposta = template
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
        assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
    }

    @Test
    @Order(2)
    @DisplayName("Nao deve permitir duplicação de Usuário")
    public void deveDuplicarUsuario(){
        service.cadastrarUsuario(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278"));

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278"));

        ResponseEntity<Usuario> resposta = template
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        //assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
        //assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
    }
    @Test
    @Order(3)
    @DisplayName("deve Listar todos os Usuários")
    public void devemostrarTodosUsuarios(){
        service.cadastrarUsuario(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278"));
        service.cadastrarUsuario(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278"));
        service.cadastrarUsuario(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278"));

        ResponseEntity<String> resposta = template
                .withBasicAuth("root", "root")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);                         
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        //assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
        //assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
    }
}
