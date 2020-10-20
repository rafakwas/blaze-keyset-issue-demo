package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "document")
public class Document {

    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

}