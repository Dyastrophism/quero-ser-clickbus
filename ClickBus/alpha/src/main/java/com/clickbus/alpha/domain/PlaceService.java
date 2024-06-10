package com.clickbus.alpha.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import com.clickbus.alpha.api.PlaceRequest;
import com.clickbus.alpha.util.QueryBuilder;
import com.github.slugify.Slugify;
import reactor.core.publisher.Flux;
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

    public Mono<Place> get(Long id) {
        return placeRepository.findById(id);
    }

    public Mono<Place> edit(Long id, PlaceRequest placeRequest) {
        return placeRepository.findById(id)
                .map(place -> PlaceMapper.updatePlaceFromDTO(place, placeRequest))
                .map(place -> place.withSlug(slg.slugify(place.name()))).
                flatMap(placeRepository::save);
    }

    public Flux<Place> list(String name) {
        var place = new Place(null, name, null, null, null, null, null);
        Example<Place> query = QueryBuilder.makeQuery(place);
        return placeRepository.findAll(query, Sort.by("name").ascending());
    }
}