package scalars;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScalarsConfiguration {

    @Bean(name = "UUID")
    public graphql.schema.GraphQLScalarType uuidScalar() {
        return UUIDScalar.UUID;
    }
}