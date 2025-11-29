package my.projects.factory.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class UUIDScalar {

    public static final GraphQLScalarType UUID = GraphQLScalarType.newScalar()
            .name("UUID")
            .description("Custom UUID scalar")
            .coercing(new Coercing<UUID, String>() {

                @Override
                public String serialize(@NonNull Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof UUID uuid) {
                        return uuid.toString();
                    }
                    throw new CoercingSerializeException("Not a UUID");
                }

                @Override
                public UUID parseValue(@NonNull Object input) throws CoercingParseValueException {
                    try {
                        return java.util.UUID.fromString(input.toString());
                    } catch (Exception e) {
                        throw new CoercingParseValueException("Invalid UUID: " + input);
                    }
                }

                @Override
                public UUID parseLiteral(@NonNull Object input) throws CoercingParseLiteralException {
                    if (input instanceof StringValue sv) {
                        return java.util.UUID.fromString(sv.getValue());
                    }
                    throw new CoercingParseLiteralException("Expected string literal");
                }
            })
            .build();
}
