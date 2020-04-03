package com.revature.controllers;

import com.revature.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){this.imageService = imageService;}

    @GetMapping(value = "/same", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSameFace(){
        return imageService.compareSameFaces("imgA.jpg", "imgB.jpg");
    }

    @GetMapping(value = "/diff", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDifferentFace(){
        return imageService.compareDifferentFaces("imgA.jpg", "imgC.jpg");
    }

}
