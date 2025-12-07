package my.projects.factory.graphql.scalars;

import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Bean
    public GraphQLScalarType uuidScalar() {
        return UUIDScalar.UUID;
    }

    /**
     * Creates a GraphQL Date scalar.
     *
     * @return the custom {@link GraphQLScalarType} representing a Date
     */
    @Bean
    public GraphQLScalarType dateScalar() {
        return DateScalar.Date;
    }

    /**
     * Configures the runtime wiring for GraphQL and registers the custom scalars.
     *
     * @param uuidScalar the UUID scalar to register in GraphQL runtime wiring
     * @param dateScalar the Date scalar to register in GraphQL runtime wiring
     * @return a {@link RuntimeWiringConfigurer} that adds the custom scalar
     */
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(
            @Qualifier("uuidScalar") GraphQLScalarType uuidScalar,
            @Qualifier("dateScalar") GraphQLScalarType dateScalar
    ) {
        return wiringBuilder -> wiringBuilder
                .scalar(uuidScalar)
                .scalar(dateScalar);
    }
}
