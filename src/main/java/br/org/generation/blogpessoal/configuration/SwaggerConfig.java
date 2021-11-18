package br.org.generation.blogpessoal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import static br.org.generation.blogpessoal.configuration.Info.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACK))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, responseMessage())
                .globalResponses(HttpMethod.POST, responseMessage())
                .globalResponses(HttpMethod.PUT, responseMessage())
                .globalResponses(HttpMethod.DELETE, responseMessage());
    }

    public static ApiInfo metadata(){
        return new ApiInfoBuilder()
                .title(TITULO)
                .description(DESCRICAO)
                .version(VERSAO)
                .license(LICENCE)
                .licenseUrl(LICENCE_URL)
                .contact(contact())
                .build();
    }

    private static Contact contact(){
        return new Contact(NOME,GITHUB,EMAIL);
    }

    private static List<Response> responseMessage(){
        return new ArrayList<Response>(){
            private static final long serialVersionUID = 1L;
            {
                add(new ResponseBuilder().code("200").description(SUCESSO).build());
                add(new ResponseBuilder().code("201").description(CRIADO).build());
                add(new ResponseBuilder().code("400").description(ERRO_NA_REQUISICAO).build());
                add(new ResponseBuilder().code("401").description(NAO_AUTORIZADO).build());
                add(new ResponseBuilder().code("403").description(PROIBIDO).build());
                add(new ResponseBuilder().code("404").description(NAO_ENCONTRADO).build());
                add(new ResponseBuilder().code("500").description(ERRO).build());
            }
        };
    }
}

