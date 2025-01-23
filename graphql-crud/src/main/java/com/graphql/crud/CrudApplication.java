package com.graphql.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
*
* Install all dependencies -> JPA , Spring Web , GRAPHQL , H2 Database
* Define Schema in resource/graphql/file.graphql
*
*
* */

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
