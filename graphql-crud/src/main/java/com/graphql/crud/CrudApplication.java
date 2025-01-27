package com.graphql.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
*
* Install all dependencies -> JPA , Spring Web , GRAPHQL , H2 Database
* Create Bean with Entity
* Define Schema in resource/graphql/file.graphql
* Create Repository with JPA
* Create Business Login in Services
* Map the endpoints withe services in the Resolver/Controller
*
* */

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
