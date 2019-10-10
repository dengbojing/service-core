package com.yichen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by anxpp.com on 2018/9/9.
 */
public class JsonUtil {

    private ObjectMapper objectMapper;

    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 对象转json
     *
     * @param object 对象
     * @return json
     */
    public String string(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 对象转json（带异常，方便自定义异常处理）
     *
     * @param object 对象
     * @return json
     * @throws JsonProcessingException 解析异常
     */
    public String toString(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return objectMapper.writeValueAsString(object);
    }

    /**
     * 字符串转对象
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     */
    public <T> T entity(String json, Class<T> valueType) {
        if (null == json) {
            return null;
        }
        if (valueType == String.class) {
            return (T) json;
        }
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
    }

    /**
     * 字符串转对象（带异常，方便自定义异常处理）
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     * @throws IOException IO异常
     */
    public <T> T toEntity(String json, Class<T> valueType) throws IOException {
        if (null == json) {
            return null;
        }
        if (valueType == String.class) {
            return (T) json;
        }
        return objectMapper.readValue(json, valueType);
    }

    /**
     * 字符串转带泛型的对象
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param type      对象是泛型时的泛型类型
     * @param <T>       泛型
     * @param <V>       泛型
     * @return 对象
     */
    public <T, V> T entity(String json, Class<T> valueType, Class<V> type) {
        if (json == null) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(valueType, type);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
    }

    /**
     * 字符串转List
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     */
    public <T> List<T> list(String json, Class<T> valueType) {
        if (json == null) {
            return new ArrayList<>(8);
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
    }

    /**
     * 字符串转List（带异常，方便自定义异常处理）
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     * @throws IOException IO异常
     */
    public <T> List<T> toList(String json, Class<T> valueType) throws IOException {
        if (json == null) {
            return new ArrayList<>(8);
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
        return objectMapper.readValue(json, javaType);
    }

    /**
     * 字符串转Set
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     */
    public <T> Set<T> set(String json, Class<T> valueType) {
        if (json == null)
            return new HashSet<>();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(LinkedHashSet.class, valueType);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
    }

    /**
     * 字符串转Set（带异常，方便自定义异常处理）
     *
     * @param json      字符串
     * @param valueType 对象类型
     * @param <T>       泛型
     * @return 对象
     * @throws IOException IO异常
     */
    public <T> Set<T> toSet(String json, Class<T> valueType) throws IOException {
        if (json == null)
            return new HashSet<>();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(LinkedHashSet.class, valueType);
        return objectMapper.readValue(json, javaType);
    }

    /**
     * 字符串转Map, key和value为字符串
     *
     * @param json 字符串
     * @return 对象
     */
    public Map<String, String> map(String json) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(LinkedHashMap.class, String.class, Object.class);
        HashMap<String, Object> result;
        try {
            result = objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
        HashMap<String, String> map = new HashMap<>(8);
        result.forEach((key, value) -> map.put(key, value instanceof String ? (String) value : string(value)));
        return map;
    }

    /**
     * 字符串转Map, key和value为字符串（带异常，方便自定义异常处理）
     *
     * @param json 字符串
     * @return 对象
     * @throws IOException IO异常
     */
    public Map<String, String> toMap(String json) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(LinkedHashMap.class, String.class, Object.class);
        HashMap<String, Object> result;
        result = objectMapper.readValue(json, javaType);
        HashMap<String, String> map = new HashMap<>();
        result.forEach((key, value) -> map.put(key, value instanceof String ? (String) value : string(value)));
        return map;
    }

    /**
     * 字符串转Map, key为字符串
     *
     * @param json      字符串
     * @param valueType value类型
     * @param <V>       value的泛型
     * @return 对象
     */
    public <V> Map<String, V> map(String json, Class<V> valueType) {
        return map(json, String.class, valueType);
    }

    /**
     * 字符串转Map, key为字符串（带异常，方便自定义异常处理）
     *
     * @param json      字符串
     * @param valueType value类型
     * @param <V>       value的泛型
     * @return 对象
     * @throws IOException IO异常
     */
    public <V> Map<String, V> toMap(String json, Class<V> valueType) throws IOException {
        return toMap(json, String.class, valueType);
    }

    /**
     * 字符串转Map
     *
     * @param json      字符串
     * @param keyType   key类型
     * @param valueType value类型
     * @param <K>       key的泛型
     * @param <V>       value的泛型
     * @return 对象
     */
    public <K, V> Map<K, V> map(String json, Class<K> keyType, Class<V> valueType) {
        if (json == null) {
            return new HashMap<>(8);
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, keyType, valueType);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据有误！");
        }
    }

    /**
     * 字符串转Map（带异常，方便自定义异常处理）
     *
     * @param json      字符串
     * @param keyType   key类型
     * @param valueType value类型
     * @param <K>       key的泛型
     * @param <V>       value的泛型
     * @return 对象
     * @throws IOException IO异常
     */
    public <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType) throws IOException {
        if (json == null) {
            return new HashMap<>(8);
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(HashMap.class, keyType, valueType);
        return objectMapper.readValue(json, javaType);
    }

}
