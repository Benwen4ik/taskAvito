package com.tech.linkShort.repositories;

import com.tech.linkShort.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link,Integer> {

    public Optional<Link> findByFullURL(String fullURL);

    public Optional<Link> findByToken(String token);
}
