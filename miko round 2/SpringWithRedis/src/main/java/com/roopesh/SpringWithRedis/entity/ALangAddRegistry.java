package com.roopesh.SpringWithRedis.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "alang_add_registry")
public class ALangAddRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    long addId;
    @Column(name = "add_statement")
    String addStatement;
}
