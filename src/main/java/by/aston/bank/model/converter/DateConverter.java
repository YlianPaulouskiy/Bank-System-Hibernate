package by.aston.bank.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;

@Converter
public class DateConverter implements AttributeConverter<String, Date> {

    @Override
    public Date convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        return Date.valueOf(attribute);
    }

    @Override
    public String convertToEntityAttribute(Date dbData) {
        if (dbData == null) return null;
        return dbData.toString();
    }

}
