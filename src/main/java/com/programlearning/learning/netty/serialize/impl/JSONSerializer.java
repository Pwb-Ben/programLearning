package com.programlearning.learning.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.programlearning.learning.netty.serialize.Serializer;
import com.programlearning.learning.netty.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {

    public static Serializer DEFAULT = new JSONSerializer();

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
