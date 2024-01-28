package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Json deserializer that obtains the raw json value in String format
 */
public class RawJsonDeserializer extends JsonDeserializer<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        final JsonNode node = mapper.readTree(jsonParser);
        return mapper.writeValueAsString(node);
    }

}
