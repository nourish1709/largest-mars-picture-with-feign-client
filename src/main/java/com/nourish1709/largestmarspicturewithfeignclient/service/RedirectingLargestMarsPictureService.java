package com.nourish1709.largestmarspicturewithfeignclient.service;

import com.nourish1709.largestmarspicturewithfeignclient.client.NasaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedirectingLargestMarsPictureService implements LargestMarsPictureService {

    private final NasaClient nasaClient;
    private final RestTemplate restTemplate;

    @Value("${nasa.api.key}")
    private String apiKey;

    @Override
    @Cacheable("largestMarsPicture")
    public Optional<byte[]> calculateLargestPicture(Long sol, String camera) {
        var allPhotos = nasaClient.getAllPhotos(sol, apiKey, camera);

        return allPhotos.findValues("img_src").parallelStream()
                .max(Comparator.comparingLong(
                        imgSrc -> restTemplate.headForHeaders(imgSrc.asText()).getContentLength()))
                .map(imgSrc -> UriComponentsBuilder.fromHttpUrl(imgSrc.asText()).build().toUri())
                .map(imgSrcURI -> restTemplate.getForObject(imgSrcURI, byte[].class));
    }
}
