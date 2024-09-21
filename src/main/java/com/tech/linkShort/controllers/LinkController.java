package com.tech.linkShort.controllers;

import com.tech.linkShort.entities.Link;
import com.tech.linkShort.entities.LinkDTO;
import com.tech.linkShort.services.LinkService;
import com.tech.linkShort.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> createLink(@RequestBody LinkDTO linkDTO){
        if (linkService.searchByFullUrl(linkDTO.getFullURL()) != null){
            return new ResponseEntity<>( Response.builder().message("Такая ссылка уже сохранена")
                    .timestamp(System.currentTimeMillis()).build(), HttpStatus.resolve(200));
        }
        String token = linkService.save(linkDTO.getFullURL());
        return new ResponseEntity<>(Response.builder().message(token)
                .timestamp(System.currentTimeMillis()).build(),HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public LinkDTO getLinkByToket(@PathVariable String token){
        Link link = linkService.searchByToken(token);
        if(link == null){
            //return ;
        }
        return LinkDTO.builder().fullURL(link.getFullURL()).build();
    }
}
