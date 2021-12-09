package com.bruno.bootcampheroes.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.bruno.bootcampheroes.constants.AppConstant.DYNAMO_ENDPOINT;
import static com.bruno.bootcampheroes.constants.AppConstant.DYNAMO_REGION;
import static com.bruno.bootcampheroes.constants.HeroConstant.HERO_TABLE_NAME;

@Component
public class CreateDynamoDBTable implements CommandLineRunner {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CreateDynamoDBTable.class);

    @Override
    public void run(String... args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(DYNAMO_ENDPOINT, DYNAMO_REGION))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        try {
            if (!client.listTables().getTableNames().contains(HERO_TABLE_NAME)) {
                Table table = dynamoDB.createTable(
                        HERO_TABLE_NAME,
                        Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                        Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                        new ProvisionedThroughput(5L, 5L)
                );
                table.waitForActive();
                LOGGER.info("Table '{}' is active!", HERO_TABLE_NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
