package com.nourish1709.largestmarspicturewithfeignclient.service;

import java.util.Optional;

public interface LargestMarsPictureService {

    Optional<byte[]> calculateLargestPicture(Long sol, String camera);
}
