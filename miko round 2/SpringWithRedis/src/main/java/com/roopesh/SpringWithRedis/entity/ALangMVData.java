package com.roopesh.SpringWithRedis.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "alang_mv_data")
public class ALangMVData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mv_id")
    long mVId;
    @Column(name = "register")
    String register;
    @Column(name = "current_value")
    int currentValue;
    @Column(name = "previous_value")
    int previousValue;
}
