package es.grupoO.FastFood.common;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface MongoContainer {
    @Container
    MongoDBContainer mongoContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
}
