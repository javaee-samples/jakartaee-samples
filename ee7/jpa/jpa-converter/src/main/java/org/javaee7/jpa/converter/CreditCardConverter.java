package org.javaee7.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Arun Gupta
 */
@Converter
public class CreditCardConverter implements AttributeConverter<CreditCard, String> {

    @Override
    public String convertToDatabaseColumn(CreditCard attribute) {
        return attribute.toString();
    }

    @Override
    public CreditCard convertToEntityAttribute(String card) {
        return new CreditCard(card);
    }

}
