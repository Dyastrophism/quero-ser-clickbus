package com.clickbus.alpha.web;

import com.clickbus.alpha.api.PlaceResponse;
import com.clickbus.alpha.domain.Place;

public class PlaceMapper {

    public static PlaceResponse fromPlaceToResponse(Place place) {
        return new PlaceResponse(
                place.name(),
                place.slug(),
                place.city(),
                place.state(),
                place.createdAt(),
                place.updatedAt()
        );
    }
}
