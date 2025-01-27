package com.graphql.crud.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_table") // Fixed table name format
@Data // Generates getters, setters, equals, hashCode, and toString
@NoArgsConstructor // No-args constructor for JPA
@AllArgsConstructor // All-args constructor if needed
@Builder // Builder pattern for ease of object creation
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
}
