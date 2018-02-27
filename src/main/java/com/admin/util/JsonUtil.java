package com.admin.util;

import com.google.gson.*;
import net.sf.json.JSONObject;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Date;

public class JsonUtil {

    private static Gson gson;

    static {
        JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };

        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateJsonSerializer)
                .registerTypeAdapter(Date.class, dateJsonDeserializer).create();
    }


    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T parseJson(Reader json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T getPerson(String jsonString, Class<T> tClass) {
        return gson.fromJson(jsonString, tClass);
    }

    /**
     * json String转为java对象
     *
     * @param jsonString
     * @return
     */
    public static JSONObject getJsonObjectFromJsonString(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString.trim());
        return jsonObject;
    }
}