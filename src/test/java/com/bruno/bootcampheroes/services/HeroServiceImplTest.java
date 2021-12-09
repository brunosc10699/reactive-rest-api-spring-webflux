package com.bruno.bootcampheroes.services;

import com.bruno.bootcampheroes.builders.HeroDocumentBuilder;
import com.bruno.bootcampheroes.documents.HeroDocument;
import com.bruno.bootcampheroes.dto.HeroDocumentDTO;
import com.bruno.bootcampheroes.repositories.HeroRepository;
import com.bruno.bootcampheroes.services.impl.HeroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class HeroServiceImplTest {

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HeroServiceImpl heroService;

    private final HeroDocument hero = HeroDocumentBuilder.hero();

    private final HeroDocumentDTO heroDTO = HeroDocumentDTO.fromDocument(hero);

    @BeforeEach
    void setUp() {
        BDDMockito.when(heroRepository.save(HeroDocumentBuilder.hero()))
                .thenReturn(hero);

        BDDMockito.when(heroRepository.findAll())
                .thenReturn(List.of(hero));

        BDDMockito.when(heroRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(hero));
    }

    @Test
    @DisplayName("(1) Should save a new hero")
    void whenGivingHeroDataReturnAHeroDTOObject() {
        HeroDocument newHero = HeroDocumentBuilder.hero();

        HeroDocumentDTO newHeroDTO = HeroDocumentDTO.fromDocument(newHero);

        StepVerifier.create(heroService.saveHero(newHeroDTO))
                .expectSubscription()
                .expectNext(heroDTO)
                .verifyComplete();
    }

    @Test
    @DisplayName("(2) Should return a flux of HeroDocumentDTO")
    void whenRequestedReturnAllHeroes() {
        StepVerifier.create(heroService.findAll())
                .expectSubscription()
                .expectNext(heroDTO)
                .verifyComplete();
    }

    @Test
    @DisplayName("(3) Should return a Hero by id")
    void whenProvidingAValidIdReturnAHero() {
        StepVerifier.create(heroService.findById(hero.getId()))
                .expectSubscription()
                .expectNext(heroDTO)
                .verifyComplete();
    }

    @Test
    @DisplayName("(4) Should update a Hero by id")
    void whenProvidingAValidIdAndValidRequestBodyThenUpdateTheHero() {
        HeroDocumentDTO newHeroDTO = HeroDocumentDTO.fromDocument(HeroDocumentBuilder.hero());
        StepVerifier.create(heroService.updateById(heroDTO.getId(), newHeroDTO))
                .expectSubscription()
                .expectNext(newHeroDTO)
                .verifyComplete();
    }

    @Test
    @DisplayName("(5) Should delete a Hero by id")
    void whenProvidingAValidIdThenDeleteTheHero() {
        BDDMockito.doNothing().when(heroRepository).deleteById(ArgumentMatchers.anyString());
        StepVerifier.create(heroService.deleteById(heroDTO.getId()))
                .expectSubscription()
                .verifyComplete();
    }
}
