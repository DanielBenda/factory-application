package my.projects.factory.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Custom GraphQL scalar type for {@link UUID}.
 * <p>
 * Provides serialization and parsing for UUIDs in GraphQL queries and mutations.
 */
public class UUIDScalar {

    /**
     * The GraphQL scalar instance for UUID.
     * Handles:
     * <ul>
     *     <li>Serialization of UUID to string</li>
     *     <li>Parsing input values to UUID</li>
     *     <li>Parsing string literals from GraphQL queries to UUID</li>
     * </ul>
     */
    public static final GraphQLScalarType UUID = GraphQLScalarType.newScalar()
            .name("UUID")
            .description("Custom UUID scalar")
            .coercing(new Coercing<UUID, String>() {

                /**
                 * Serialize a UUID object to a string for GraphQL responses.
                 *
                 * @param dataFetcherResult the value to serialize, expected to be a UUID
                 * @return the string representation of the UUID
                 * @throws CoercingSerializeException if the input is not a UUID
                 */
                @Override
                public String serialize(@NonNull Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof UUID uuid) {
                        return uuid.toString();
                    }
                    throw new CoercingSerializeException("Not a UUID");
                }

                /**
                 * Parse an input value (variable) into a UUID.
                 *
                 * @param input the input value, expected to be a string representation of a UUID
                 * @return the parsed UUID
                 * @throws CoercingParseValueException if the input cannot be converted to UUID
                 */
                @Override
                public UUID parseValue(@NonNull Object input) throws CoercingParseValueException {
                    try {
                        return java.util.UUID.fromString(input.toString());
                    } catch (Exception e) {
                        throw new CoercingParseValueException("Invalid UUID: " + input);
                    }
                }

                /**
                 * Parse a literal value from a GraphQL query into a UUID.
                 *
                 * @param input the literal value, expected to be a {@link StringValue}
                 * @return the parsed UUID
                 * @throws CoercingParseLiteralException if the input is not a string literal
                 */
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