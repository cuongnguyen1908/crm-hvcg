package com.hvcg.api.crm.config;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;


public class MyObjectMapper extends ObjectMapper {
    public MyObjectMapper() {
        registerModule(new MyModule());
    }

}

@Configuration
class MyModule extends SimpleModule {

    public MyModule() {
        addDeserializer(String.class, new StdScalarDeserializer<String>  (String.class) {
            @Override
            public String deserialize(JsonParser jp, DeserializationContext  ctxt) throws IOException,
                    JsonProcessingException {
                return StringUtils.trim(jp.getValueAsString().replaceAll("\\s+", " "));
            }
        });
    }
}
