package com.tech.linkShort.controllers;

import com.tech.linkShort.entities.Link;
import com.tech.linkShort.entities.LinkDTO;
import com.tech.linkShort.services.LinkService;
import com.tech.linkShort.util.LinkException;
import com.tech.linkShort.util.LinkValidation;
import com.tech.linkShort.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpRequest;

import static com.tech.linkShort.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    private final LinkValidation linkValidation;

    @Autowired
    public LinkController(LinkService linkService, LinkValidation linkValidation) {
        this.linkService = linkService;
        this.linkValidation = linkValidation;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> createLink(@RequestBody LinkDTO linkDTO, BindingResult bindingResult) {
        linkValidation.validate(Link.builder().fullURL(linkDTO.getFullURL()).build(),bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        String token = linkService.save(linkDTO.getFullURL());
        return new ResponseEntity<>(Response.builder().message(token)
                .timestamp(System.currentTimeMillis()).build(),HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<Void> getLinkByToket(@PathVariable String token){
        Link link = linkService.searchByToken(token);
        if(link == null){
            //return ;
        }
        //  return LinkDTO.builder().fullURL(link.getFullURL()).build();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getFullURL())).build();
    }

    @ExceptionHandler
    public ResponseEntity<Response> handlerException(LinkException ex){
        return  new ResponseEntity<>(Response.builder().message(ex.getMessage()).timestamp(System.currentTimeMillis()).build(),HttpStatus.BAD_REQUEST) ;
    }
}
