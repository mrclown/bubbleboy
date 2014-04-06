package com.clowndata.util;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 2014.
 */
public class DateSerializer extends JsonSerializer<Date> {

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(Date date, org.codehaus.jackson.JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jgen.writeString(new SimpleDateFormat(DATE_TIME_FORMAT).format(date));
    }

}


