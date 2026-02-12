package my.projects.factory.graphql.scalars;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jspecify.annotations.NonNull;

/**
 * Custom GraphQL scalar type for {@link Long}.
 * <p>
 * Provides serialization and parsing for Longs in GraphQL queries and mutations.
 */
public class LongScalar {

    /**
     * The GraphQL scalar instance for Long.
     * <p>
     * Handles:
     * <ul>
     *     <li>Serialization of {@link Long} values for GraphQL responses</li>
     *     <li>Parsing input values (variables) into {@link Long}</li>
     *     <li>Parsing numeric and string literals from GraphQL queries into {@link Long}</li>
     * </ul>
     */
    public static final GraphQLScalarType LONG = GraphQLScalarType.newScalar()
            .name("Long")
            .description("Custom Long scalar")
            .coercing(new Coercing<Long, Long>() {

                /**
                 * Serialize a Long value for GraphQL responses.
                 */
                @Override
                public Long serialize(@NonNull Object dataFetcherResult)
                        throws CoercingSerializeException {

                    if (dataFetcherResult instanceof Long l) {
                        return l;
                    }
                    if (dataFetcherResult instanceof Number n) {
                        return n.longValue();
                    }
                    throw new CoercingSerializeException(
                            "Expected a Long but was: " + dataFetcherResult.getClass().getSimpleName()
                    );
                }

                /**
                 * Parse an input value (variables) into a Long.
                 */
                @Override
                public Long parseValue(@NonNull Object input)
                        throws CoercingParseValueException {

                    try {
                        if (input instanceof Number n) {
                            return n.longValue();
                        }
                        return Long.parseLong(input.toString());
                    } catch (Exception e) {
                        throw new CoercingParseValueException("Invalid Long value: " + input);
                    }
                }

                /**
                 * Parse a literal value from a GraphQL query into a Long.
                 */
                @Override
                public Long parseLiteral(@NonNull Object input)
                        throws CoercingParseLiteralException {

                    if (input instanceof graphql.language.IntValue iv) {
                        return iv.getValue().longValue();
                    }
                    if (input instanceof graphql.language.StringValue sv) {
                        try {
                            return Long.parseLong(sv.getValue());
                        } catch (NumberFormatException e) {
                            throw new CoercingParseLiteralException("Invalid Long literal: " + sv.getValue());
                        }
                    }
                    throw new CoercingParseLiteralException("Expected IntValue or StringValue");
                }
            })
            .build();
}
