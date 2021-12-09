package com.bruno.bootcampheroes.services;

import com.bruno.bootcampheroes.dto.HeroDocumentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HeroService {

    Mono<HeroDocumentDTO> saveHero(HeroDocumentDTO hero);

    Flux<HeroDocumentDTO> findAll();

    Mono<HeroDocumentDTO> findById(String id);

    Mono<HeroDocumentDTO> updateById(String id, HeroDocumentDTO heroDTO);

    Mono<Void> deleteById(String id);
}
