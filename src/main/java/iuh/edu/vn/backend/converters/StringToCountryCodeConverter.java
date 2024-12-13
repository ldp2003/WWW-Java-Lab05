package iuh.edu.vn.backend.converters;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCountryCodeConverter implements Converter<String, CountryCode> {
    @Override
    public CountryCode convert(String source) {
        return CountryCode.getByCode(source);
    }
}