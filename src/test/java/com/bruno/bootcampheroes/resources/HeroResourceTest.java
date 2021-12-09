package com.bruno.bootcampheroes.resources;

import com.bruno.bootcampheroes.builders.HeroDocumentBuilder;
import com.bruno.bootcampheroes.documents.HeroDocument;
import com.bruno.bootcampheroes.dto.HeroDocumentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.bruno.bootcampheroes.constants.HeroConstant.HERO_URN;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    private HeroDocument hero = HeroDocumentBuilder.hero();

    private HeroDocumentDTO heroDTO = HeroDocumentDTO.fromDocument(hero);

    @Test
    @DisplayName("(1) Should return 2xx status code when saving a new hero")
    void whenRegisteringAHeroReturn2xxSuccessfulStatusCode() {
        webTestClient.post()
                .uri(HERO_URN)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(heroDTO)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("(2) Should return 200 status code when searching for all heroes")
    void whenFindAllHeroesRequestedReturn200OkStatus() {
        webTestClient.get()
                .uri(HERO_URN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("(3) Should return 404 Not Found status code when searching for a hero")
    void whenSearchingForAHeroWithAnInvalidIdReturn404NotFoundStatusCode() {
        webTestClient.get()
                .uri(HERO_URN + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("(4) Should return 200 OK status code when updating a hero")
    void whenUpdatingAHeroWithAValidIdReturn200OkStatusCode() {
        webTestClient.put()
                .uri(HERO_URN + "/" + heroDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(heroDTO)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("(5) Should return 204 No Content status code when deleting a hero")
    void whenDeletingAHeroWithAValidIdReturn204NoContentStatusCode() {
        webTestClient.delete()
                .uri(HERO_URN + "/" + hero.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }
}

