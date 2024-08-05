package com.prunnytest.bookstore.model;

import com.prunnytest.bookstore.model.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String user;
    private String password;
    private Roles role;
}
