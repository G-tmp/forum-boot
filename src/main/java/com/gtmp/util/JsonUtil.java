package com.gtmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtmp.POJO.Notification;
import com.gtmp.POJO.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    public static String toJsonString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T toObject(String str, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Class aClass = objectMapper.readValue(str, clazz.getClass());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T fromJSON(final String jsonPacket, final TypeReference<T> type) {
        T data = null;

        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            // Handle the problem
        }
        return data;
    }


    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("int", 1);
        map.put("string", "sd");
        map.put("float", 1.2f);
        map.put("time", new Date());
        map.put("user", new User().setId(1));
        System.out.println(map);
//        String s = JSONObject.toJSONString(map);
//        Map map1 = JSONObject.parseObject(s,Map.class);
//        System.out.println(map1);

        String a = JsonUtil.toJsonString(map);
        System.out.println(a);

        Map map1 = (Map) JsonUtil.fromJSON(a, new TypeReference<Map>() {
        });
        User u = (User) map1.get("user");
        System.out.println(u);
    }
}
