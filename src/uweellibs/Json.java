package uweellibs;

public class Json {
    static final com.google.gson.Gson Gson = new com.google.gson.Gson();

    public static java.lang.Object FromJson(java.lang.String json, java.lang.reflect.Type type) {
        return Gson.fromJson(json, type);
    }

    public static java.lang.String ToJson(Object object) {
        return Gson.toJson(object);
    }
}
