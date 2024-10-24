package mouda.backend.common.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import mouda.backend.moim.domain.FilterType;

@Component
public class FilterTypeConverter implements Converter<String, FilterType> {

	@Override
	public FilterType convert(String source) {
		try {
			return FilterType.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
			return FilterType.ALL; // 에러 발생시 기본 설정은 ALL
		}
	}
}
