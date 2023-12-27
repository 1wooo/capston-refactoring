package com.example.demo.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
