package com.education.resources.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampDeserializer extends JsonDeserializer<Timestamp> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Date date = null;
        try {
            date  = sdf.parse(jsonParser.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date ==null){
            return null;
        }
        return new Timestamp(date.getTime());

    }
}
