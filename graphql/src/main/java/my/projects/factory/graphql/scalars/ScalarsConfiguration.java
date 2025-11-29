package my.projects.factory.graphql.scalars;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ScalarsConfiguration {

    @Bean(name = "UUID")
    public graphql.schema.GraphQLScalarType uuidScalar() {
        return UUIDScalar.UUID;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType uuidScalar) {
        return wiringBuilder -> wiringBuilder.scalar(uuidScalar);
    }
}