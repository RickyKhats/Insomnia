package uweellibs;

import java.lang.reflect.Type;
import java.util.prefs.Preferences;

public class PlayerPrefs {

    private static final String dataPath = "SOFTWARE\\Insomnia\\";

    static Preferences Objects = Preferences.userRoot().node(Preferences.userRoot() + dataPath + "object");
    static Preferences Floats = Preferences.userRoot().node(Preferences.userRoot() + dataPath + "float");
    static Preferences Integers = Preferences.userRoot().node(Preferences.userRoot() + dataPath + "int");
    static Preferences Booleans = Preferences.userRoot().node(Preferences.userRoot() + dataPath + "boolean");
    static Preferences Strings = Preferences.userRoot().node(Preferences.userRoot() + dataPath + "string");


    public static void SetObject(String key, Object object) {
        Objects.put(key, Json.ToJson(object));
    }
    public static Object GetObject(String key, Type type) {
        return Json.FromJson(Objects.get(key, "{}"), type);
    }

    public static void SetFloat(String key, float value) {
        Floats.putFloat(key, value);
    }
    public static float GetFloat(String key) {
        return Floats.getFloat(key, 0);
    }

    public static void SetInt(String key, int value) {
        Integers.putInt(key, value);
    }
    public static float GetInt(String key) {
        return Integers.getInt(key, 0);
    }

    public static void SetBoolean(String key, boolean value) {
        Booleans.putBoolean(key, value);
    }
    public static boolean GetBoolean(String key) {
        return Booleans.getBoolean(key, false);
    }

    public static void SetString(String key, String value) {
        Strings.put(key, value);
    }
    public static String GetString(String key) {
        return Strings.get(key, null);
    }

}

