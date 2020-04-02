package com.pyropy.usereats.converter;

import com.pyropy.usereats.model.OrderStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, OrderStatus> {

    @Override
    public OrderStatus convert(String source) {
        try {
            return OrderStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException err) {
            return null;
        }
    }
}
