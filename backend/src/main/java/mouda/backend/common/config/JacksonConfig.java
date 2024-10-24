package mouda.backend.common.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class JacksonConfig {

	@Bean
	public JavaTimeModule javaTimeModule() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		DateTimeFormatter dateFormat = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat))
			.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormat))
			.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat))
			.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormat));

		return javaTimeModule;
	}
}
