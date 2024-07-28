package com.prunnytest.bookstore.model;

import com.prunnytest.bookstore.model.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Roles role;

}
