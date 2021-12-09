package com.bruno.bootcampheroes.builders;

import com.bruno.bootcampheroes.documents.HeroDocument;

public class HeroDocumentBuilder {

    public static HeroDocument hero() {
        return HeroDocument.builder()
                .id("dfcecc9d-8d66-4e04-9c75-132693c04b89")
                .name("Bruno")
                .role("Specialist")
                .media("bruno")
                .build();
    }
}
