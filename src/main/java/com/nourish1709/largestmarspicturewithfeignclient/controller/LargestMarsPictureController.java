package com.nourish1709.largestmarspicturewithfeignclient.controller;

import com.nourish1709.largestmarspicturewithfeignclient.service.LargestMarsPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class LargestMarsPictureController {

    private final LargestMarsPictureService largestMarsPictureService;

    @GetMapping(value = "/mars/pictures/largest", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getLargestPicture(@RequestParam Long sol, @RequestParam(required = false) String camera) {
        var largestPicture = largestMarsPictureService.calculateLargestPicture(sol, camera)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pictures found"));
        return ResponseEntity.ok(largestPicture);
    }
}
