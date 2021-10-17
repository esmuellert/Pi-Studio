package site.pistudio.backend.configurations;

import org.springframework.cloud.gcp.data.datastore.core.convert.DatastoreCustomConversions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class ConverterConfiguration {

    static final Converter<LocalDateTime, String> LOCAL_DATE_TIME_STRING_CONVERTER_STRING_CONVERTER =
            new Converter<LocalDateTime, String>() {
                @Override
                public String convert(LocalDateTime localDateTime) {
                    return localDateTime.toString();
                }
            };

    static final Converter<String, LocalDateTime> STRING_LOCAL_DATE_TIME_CONVERTER_CONVERTER =
            new Converter<String, LocalDateTime>() {
                @Override
                public LocalDateTime convert(String s) {
                    return LocalDateTime.parse(s);
                }
            };

    @Bean
    public DatastoreCustomConversions datastoreCustomConversions() {
        return new DatastoreCustomConversions(
                Arrays.asList(LOCAL_DATE_TIME_STRING_CONVERTER_STRING_CONVERTER,
                        STRING_LOCAL_DATE_TIME_CONVERTER_CONVERTER));
    }
}
