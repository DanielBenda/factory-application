package my.projects.factory.graphql.scalars;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateScalarTest {

    @Test
    void testSerializeValidDate() {
        Date date = Date.from(Instant.parse("2026-04-05T12:30:00Z"));

        String serialized = (String) DateScalar.Date.getCoercing()
                .serialize(date, GraphQLContext.newContext().build(), Locale.getDefault());

        assertEquals("2026-04-05T12:30:00Z", serialized);
    }

    @Test
    void testSerializeInvalidType() {
        String notADate = "I am not a date";

        assertThrows(CoercingSerializeException.class, () ->
                DateScalar.Date.getCoercing().serialize(notADate, GraphQLContext.newContext().build(), Locale.getDefault())
        );
    }

    @Test
    void testParseValueValid() {
        String isoString = "2026-04-05T12:30:00Z";

        Date parsed = (Date) DateScalar.Date.getCoercing()
                .parseValue(isoString, GraphQLContext.newContext().build(), Locale.getDefault());

        assertNotNull(parsed);
        assertEquals(Instant.parse(isoString), parsed.toInstant());
    }

    @Test
    void testParseValueInvalid() {
        String isoString = "invalid-date";

        assertThrows(CoercingParseValueException.class, () -> DateScalar.Date.getCoercing()
                .parseValue(isoString, GraphQLContext.newContext().build(), Locale.getDefault()));
    }

    @Test
    void testParseLiteralValid() {
        String isoString = "2026-04-05T12:30:00Z";

        Date parsed = (Date) DateScalar.Date.getCoercing()
                .parseValue(isoString, GraphQLContext.newContext().build(), Locale.getDefault());

        assertNotNull(parsed);
        assertEquals("2026-04-05T12:30:00Z", parsed.toInstant().toString());
    }

    @Test
    void testParseLiteralInvalidString() {
        StringValue literal = new StringValue("invalid-date");

        assertThrows(CoercingParseLiteralException.class, () -> DateScalar.Date.getCoercing()
                .parseLiteral(literal, CoercedVariables.emptyVariables(), GraphQLContext.newContext().build(), Locale.getDefault()));
    }
}