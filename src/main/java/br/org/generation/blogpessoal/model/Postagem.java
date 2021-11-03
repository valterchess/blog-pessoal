package br.org.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Table(name = "tb_postagens")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "atributo título obrigatório")
    @Size(min = 5, max = 100,message = "O atributo titulo deve conter no mínimo 5 e" +
            " no maximo 100 caracteres.")
    private String titulo;

    @NotNull(message = "atributo texto obrigatório")
    @Size(min = 5, max = 1000,message = "O atributo texto deve conter no mínimo 10 e" +
            " no maximo 1000 caracteres.")
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data = new java.sql.Date(System.currentTimeMillis());

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTema(Tema tema){
        this.tema = tema;
    }
    public Tema getTema() {return tema;}

}
