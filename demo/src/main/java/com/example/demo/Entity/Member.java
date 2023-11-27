package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "idmember")
    private String memberId;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", length = 10)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}
