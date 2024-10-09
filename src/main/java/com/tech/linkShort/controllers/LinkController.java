package com.tech.linkShort.controllers;

import com.tech.linkShort.entities.Link;
import com.tech.linkShort.entities.LinkDTO;
import com.tech.linkShort.services.LinkService;
import com.tech.linkShort.util.LinkException;
import com.tech.linkShort.util.LinkValidation;
import com.tech.linkShort.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Link", description = "link api")
public class LinkController {

    private final LinkService linkService;

    private final LinkValidation linkValidation;

    @Autowired
    public LinkController(LinkService linkService, LinkValidation linkValidation) {
        this.linkService = linkService;
        this.linkValidation = linkValidation;
    }

    @PostMapping("/add")
    @Operation(tags = "link", summary = "get token of link")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Get token of link",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LinkDTO.class)))
                    })
    })
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
    @Operation(tags = "link", summary = "get url by token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "Found",
                    description = "Get URL by token",
                    content = {
                            @Content(
                                    mediaType = "html",
                                    array = @ArraySchema(schema = @Schema(implementation = LinkDTO.class)))
                    })
    })
    public ResponseEntity<Void> getLinkByToket(@PathVariable String token){
        Link link = linkService.searchByToken(token);
        if(link == null){
            //return ;  // TODO
        }
        //  return LinkDTO.builder().fullURL(link.getFullURL()).build();
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getFullURL())).build();
    }

    @ExceptionHandler
    public ResponseEntity<Response> handlerException(LinkException ex){
        return  new ResponseEntity<>(Response.builder().message(ex.getMessage()).timestamp(System.currentTimeMillis()).build(),HttpStatus.BAD_REQUEST) ;
    }
}
