package com.rayllanderson.raybank.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;
    @ManyToOne
    private User user;
}
