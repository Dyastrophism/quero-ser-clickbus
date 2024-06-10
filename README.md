## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data + R2DBC](https://docs.spring.io/spring-framework/reference/data-access/r2dbc.html)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Slugify](https://github.com/slugify/slugify)

## Práticas adotadas

- SOLID
- Testes automatizados
- Consultas com filtros dinâmicos usando o Query By Example
- API reativa na web e na camada de banco
- Uso de DTOs para a API
- Injeção de Dependências
- Geração automática do Swagger com a OpenAPI 3
- Geração de slugs automática com o Slugify
- Auditoria sobre criação e atualização da entidade

## Como Executar

### Localmente
- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar place-service/target/place-service-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
