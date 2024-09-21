package com.tech.linkShort.services;

import com.tech.linkShort.entities.Link;
import com.tech.linkShort.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final Sqids sqids;


    @Autowired
    public LinkService(LinkRepository linkRepository, Sqids sqids) {
        this.linkRepository = linkRepository;
        this.sqids = sqids;
    }

    public Link searchByFullUrl(String fullUrl) {
        return linkRepository.findByFullURL(fullUrl).orElse(null);
    }

    public void save(String url) {
        String token = sqids.encode(List.of(randomLong()));
        Link link = Link.builder().fullURL(url).token(token).build();
        linkRepository.save(link);
    }

    public Link searchByToken(String token) {
        return linkRepository.findByToken(token).orElse(null);
    }

    private long randomLong() {
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }
}
