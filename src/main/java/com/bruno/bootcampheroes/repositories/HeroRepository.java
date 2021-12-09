package com.bruno.bootcampheroes.repositories;

import com.bruno.bootcampheroes.documents.HeroDocument;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface HeroRepository extends DynamoDBCrudRepository<HeroDocument, String> {
}
