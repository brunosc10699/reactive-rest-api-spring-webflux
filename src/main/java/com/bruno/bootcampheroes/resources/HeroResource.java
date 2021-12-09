package com.bruno.bootcampheroes.resources;

import com.bruno.bootcampheroes.dto.HeroDocumentDTO;
import com.bruno.bootcampheroes.services.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.bruno.bootcampheroes.constants.HeroConstant.HERO_URN;

@RestController
@RequestMapping(value = HERO_URN)
@RequiredArgsConstructor
public class HeroResource {

    private final HeroService heroService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HeroResource.class);

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mono<HeroDocumentDTO>> save(@Valid @RequestBody HeroDocumentDTO hero) {
        Mono<HeroDocumentDTO> monoDTO = heroService.saveHero(hero);
        LOGGER.info("New Hero registered!");
        return ResponseEntity.status(201).body(monoDTO);
    }

    @GetMapping
    public ResponseEntity<Flux<HeroDocumentDTO>> findAll() {
        LOGGER.info("All Heroes requested!");
        return ResponseEntity.ok(heroService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Mono<HeroDocumentDTO>> findById(@PathVariable String id) {
        LOGGER.info("Hero data requested by id {}", id);
        return ResponseEntity.ok(heroService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Mono<HeroDocumentDTO>> updateById(@PathVariable String id, @Valid @RequestBody HeroDocumentDTO heroDTO) {
        LOGGER.info("Hero update requested by id {}", id);
        return ResponseEntity.ok(heroService.updateById(id, heroDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable String id) {
        LOGGER.info("Hero deletion requested by id {}", id);
        heroService.deleteById(id);
        LOGGER.info("Hero deleted successfully by id {}", id);
        return ResponseEntity.noContent().build();
    }
}
