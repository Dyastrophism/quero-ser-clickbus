package com.clickbus.alpha.domain;

import com.clickbus.alpha.api.PlaceRequest;
import com.github.slugify.Slugify;
import reactor.core.publisher.Mono;

public class PlaceService {

    private final PlaceRepository placeRepository;
    private final Slugify slg;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        var place = new Place(
                null, placeRequest.name(), slg.slugify(placeRequest.name()),
                placeRequest.city(), placeRequest.state(),
                null, null);
        return placeRepository.save(place);
    }
}
