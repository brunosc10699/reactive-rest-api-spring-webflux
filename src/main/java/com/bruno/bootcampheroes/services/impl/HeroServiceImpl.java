package com.bruno.bootcampheroes.services.impl;

import com.bruno.bootcampheroes.documents.HeroDocument;
import com.bruno.bootcampheroes.dto.HeroDocumentDTO;
import com.bruno.bootcampheroes.repositories.HeroRepository;
import com.bruno.bootcampheroes.services.HeroService;
import com.bruno.bootcampheroes.services.exceptions.ExistingResourceException;
import com.bruno.bootcampheroes.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    @Override
    public Mono<HeroDocumentDTO> saveHero(HeroDocumentDTO heroDTO) {
        if (heroDTO.getId() != null) heroDTO.setId(generateId(heroDTO.getId()));
        Mono<HeroDocument> monoHero = Mono.just(heroRepository.save(fromDTO(heroDTO)));
        return toDTO(monoHero);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<HeroDocumentDTO> findAll() {
        return Flux.fromIterable(heroRepository.findAll()).map(HeroDocumentDTO::fromDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<HeroDocumentDTO> findById(String id) {
        Mono<HeroDocument> monoHero = Mono.justOrEmpty(
                heroRepository.findById(id)
                        .orElseThrow(
                                () -> new ObjectNotFoundException("Hero not found by id '" + id + "'")
                        )
        );
        return toDTO(monoHero);
    }

    @Override
    public Mono<HeroDocumentDTO> updateById(String id, HeroDocumentDTO heroDTO) {
        findById(id);
        HeroDocument hero = fromDTO(heroDTO);
        hero.setId(id);
        Mono<HeroDocument> monoHero = Mono.just(heroRepository.save(hero));
        return toDTO(monoHero);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        findById(id);
        heroRepository.deleteById(id);
        return Mono.empty();
    }

    private Mono<HeroDocumentDTO> toDTO(Mono<HeroDocument> monoHero) {
        Mono<HeroDocumentDTO> heroDTO = monoHero.map(HeroDocumentDTO::fromDocument).single();
        return heroDTO;
    }

    private HeroDocument fromDTO(HeroDocumentDTO hero) {
        return HeroDocument.builder()
                .id(hero.getId())
                .name(hero.getName())
                .role(hero.getRole())
                .media(hero.getMedia())
                .build();
    }

    private String generateId(String id) {
        if (checkUUID(id)) {
            checkExistingId(id);
            return id;
        }
        return UUID.randomUUID().toString();
    }

    private Boolean checkUUID(String uuid) {
        if (uuid.length() == 36) {
            String[] hexadecimalArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            String[] alphaNumericArray = {"8", "9", "a", "b"};
            String x = uuid.substring(14, 15);
            String y = uuid.substring(19, 20);
            if (Arrays.stream(hexadecimalArray).anyMatch(element -> element.equals(x)) &&
                    Arrays.stream(alphaNumericArray).anyMatch(element -> element.equals(y))) return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    private Boolean checkExistingId(String id) {
        if (heroRepository.existsById(id))
            throw new ExistingResourceException("The id '" + id + "' provided is not valid!");
        return false;
    }

}
