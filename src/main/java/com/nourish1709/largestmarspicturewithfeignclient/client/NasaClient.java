package com.nourish1709.largestmarspicturewithfeignclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${nasa.feign.client.name}", url = "${nasa.api.host}")
public interface NasaClient {

    @GetMapping("${nasa.api.photos.path}")
    JsonNode getAllPhotos(@RequestParam Long sol, @RequestParam("api_key") String apiKey, @RequestParam(required = false) String camera);
}
