package com.tech.linkShort.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "links")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_url")
    private String fullURL;

    @Column(name = "token")
    private String token;
}
