package my.projects.factory.graphql.scalars;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * Configuration class for custom GraphQL scalar types.
 * <p>
 * Currently registers a UUID scalar for use in GraphQL schemas.
 */
@Configuration
public class ScalarsConfiguration {

    /**
     * Creates a GraphQL UUID scalar.
     *
     * @return the custom {@link GraphQLScalarType} representing a UUID
     */
    @Bean(name = "UUID")
    public GraphQLScalarType uuidScalar() {
        return UUIDScalar.UUID;
    }

    /**
     * Configures the runtime wiring for GraphQL and registers the custom scalars.
     *
     * @param uuidScalar the UUID scalar to register in GraphQL runtime wiring
     * @return a {@link RuntimeWiringConfigurer} that adds the custom scalar
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(GraphQLScalarType uuidScalar) {
        return wiringBuilder -> wiringBuilder.scalar(uuidScalar);
    }
}
