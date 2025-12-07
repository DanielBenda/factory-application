package my.projects.factory.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Custom GraphQL scalar type for {@link Date}.
 * <p>
 * Provides serialization and parsing for dates in GraphQL queries and mutations.
 */
public class DateScalar {

    public static final GraphQLScalarType Date = GraphQLScalarType.newScalar()
            .name("Date")
            .description("Custom Date scalar (ISO-8601)")
            .coercing(new Coercing<Date, String>() {

                /**
                 * The GraphQL scalar instance for Date.
                 * Handles:
                 * <ul>
                 *     <li>Serialization of Date to string</li>
                 *     <li>Parsing input values to Date</li>
                 *     <li>Parsing string literals from GraphQL queries to Date</li>
                 * </ul>
                 */
                @Override
                public String serialize(@NonNull Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof Date date) {
                        return date.toInstant().toString(); // ISO-8601
                    }
                    throw new CoercingSerializeException("Not a Date");
                }

                /**
                 * Parse an input value (variable) into a Date.
                 *
                 * @param input the input value, expected to be a string representation of a Date
                 * @return the parsed Date
                 * @throws CoercingParseValueException if the input cannot be converted to Date
                 */
                @Override
                public Date parseValue(@NonNull Object input) throws CoercingParseValueException {
                    try {
                        Instant instant = Instant.parse(input.toString());
                        return java.util.Date.from(instant);
                    } catch (DateTimeParseException e) {
                        throw new CoercingParseValueException("Invalid ISO-8601 date: " + input);
                    }
                }

                /**
                 * Parse a literal value from a GraphQL query into a Date.
                 *
                 * @param input the literal value, expected to be a {@link StringValue}
                 * @return the parsed Date
                 * @throws CoercingParseLiteralException if the input is not a string literal
                 */
                @Override
                public Date parseLiteral(@NonNull Object input) throws CoercingParseLiteralException {
                    if (input instanceof StringValue sv) {
                        try {
                            Instant instant = Instant.parse(sv.getValue());
                            return java.util.Date.from(instant);
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseLiteralException("Invalid ISO-8601 literal: " + sv.getValue());
                        }
                    }
                    throw new CoercingParseLiteralException("Expected StringValue");
                }
            })
            .build();
}
