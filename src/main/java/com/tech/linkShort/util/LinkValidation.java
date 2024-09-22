package com.tech.linkShort.util;

import com.tech.linkShort.entities.Link;
import com.tech.linkShort.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class LinkValidation implements Validator {

    private final LinkService linkService;

    @Autowired
    public LinkValidation(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Link.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Link url = (Link) target;
        if (linkService.searchByFullUrl(url.getFullURL()) != null){
            errors.rejectValue("fullURL","Такая ссылка уже сохранена");
        }
        if (!isValidURL(url.getFullURL())){
            errors.rejectValue("fullURL","Ссылка является некорректной");
        }
    }


    boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

}
