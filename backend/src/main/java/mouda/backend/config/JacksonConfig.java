package mouda.backend.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
@EnableConfigurationProperties(DateTimeFormat.class)
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper(DateTimeFormat dateTimeFormat) {
		return new ObjectMapper()
			.registerModule(javaTimeModule(dateTimeFormat));
	}

	@Bean
	public JavaTimeModule javaTimeModule(DateTimeFormat dateTimeFormat) {
		JavaTimeModule javaTimeModule = new JavaTimeModule();

		javaTimeModule.addSerializer(LocalDate.class,
				new LocalDateSerializer(DateTimeFormatter.ofPattern(dateTimeFormat.getDate())))
			.addSerializer(LocalTime.class,
				new LocalTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat.getTime())))
			.addDeserializer(LocalDate.class,
				new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat.getDate())))
			.addDeserializer(LocalTime.class,
				new LocalTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat.getTime())));

		return javaTimeModule;
	}
}
