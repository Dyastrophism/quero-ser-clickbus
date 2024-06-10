package com.clickbus.alpha.web;

import com.clickbus.alpha.api.PlaceRequest;
import com.clickbus.alpha.api.PlaceResponse;
import com.clickbus.alpha.domain.PlaceMapper;
import com.clickbus.alpha.domain.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Mono<PlaceResponse>> create(@RequestBody @Valid PlaceRequest request) {
        var placeResponse = placeService.create(request).map(PlaceMapper::toResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<PlaceResponse>> get(@PathVariable("id") Long id) {
        return placeService.get(id).
                map(place -> ResponseEntity.ok(PlaceMapper.toResponse(place))).
                defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PatchMapping("{id}")
    public Mono<PlaceResponse> edit(@PathVariable("id") Long id, @RequestBody PlaceRequest request) {
        return placeService.edit(id, request).map(PlaceMapper::toResponse);
    }

    @GetMapping
    public Flux<PlaceResponse> list(@RequestParam(required = false) String name) {
        return placeService.list(name).map(PlaceMapper::toResponse);
    }
}
