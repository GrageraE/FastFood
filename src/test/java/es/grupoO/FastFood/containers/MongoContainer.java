package es.grupoO.FastFood.containers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface MongoContainer {
    @Container
    @ServiceConnection
    MongoDBContainer mongoContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
}
