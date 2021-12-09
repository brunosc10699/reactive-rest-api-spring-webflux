package com.bruno.bootcampheroes.dto;

import com.bruno.bootcampheroes.documents.HeroDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HeroDocumentDTO {

    private String id;

    @NotEmpty(message = "Field 'Name' must not be empty!")
    private String name;

    @NotEmpty(message = "Field 'Name' must not be empty!")
    private String role;

    private String media;

    public static HeroDocumentDTO fromDocument(HeroDocument hero) {
        return HeroDocumentDTO.builder()
                .id(hero.getId())
                .name(hero.getName())
                .role(hero.getRole())
                .media(hero.getMedia())
                .build();
    }
}
