package com.alespotify.main.util;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
class ObjectIdToStringConverter implements Converter<ObjectId, String> {

    @Override
    public String convert(ObjectId source) {
        return source == null ? null : source.toHexString();
    }
}

@Component
@ReadingConverter
class StringToObjectIdConverter implements Converter<String, ObjectId> {

    @Override
    public ObjectId convert(String source) {
        return source == null ? null : new ObjectId(source);
    }
}