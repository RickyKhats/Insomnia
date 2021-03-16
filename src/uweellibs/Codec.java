package uweellibs;

public class Codec {
    public final static String[] ru = {
            "а", "б", "в", "г", "д",
            "е", "ё", "ж", "з", "и",
            "й", "к", "л", "м", "н",
            "о", "п", "р", "с", "т",
            "у", "ф", "х", "ц", "ч",
            "ш", "щ", "ь", "ы", "ъ",
            "э", "ю", "я"
    };
    public final static String[] RU = {
            "А", "Б", "В", "Г", "Д",
            "Е", "Ё", "Ж", "З", "И",
            "Й", "К", "Л", "М", "Н",
            "О", "П", "Р", "С", "Т",
            "У", "Ф", "Х", "Ц", "Ч",
            "Ш", "Щ", "Ь", "Ы", "Ъ",
            "Э", "Ю", "Я"
    };
    public final static String[] en = {
            "a", "b", "c", "d",
            "e", "f", "g", "h",
            "i", "j", "k", "l",
            "m", "n", "o", "p",
            "q", "r", "s", "t",
            "u", "v", "w", "x",
            "y", "z"
    };
    public final static String[] EN = {
            "A", "B", "C", "D",
            "E", "F", "G", "H",
            "I", "J", "K", "L",
            "M", "N", "O", "P",
            "Q", "R", "S", "T",
            "U", "V", "W", "X",
            "Y", "Z"
    };

    public final static char quote = '"';

    public final static String space = " ";

    final static String[] codes_RU = {
            "99992", "98983", "97974", "96965", "69696",
            "80807", "81818", "95959", "45671", "79792",
            "82827", "94946", "68685", "78784", "84843",
            "93938", "67678", "77639", "9292", "9191",
            "65675", "7669", "85853", "9090", "6464",
            "75679", "86586", "89589", "6363", "7155",
            "12374", "85568", "72572"
    };

    final static String[] codes_ru = {
            "4030", "1655", "7534", "3756",
            "5067", "1355", "5733", "3750",
            "6454", "5313", "3644", "5444",
            "5737", "1351", "6753", "1868",
            "8644", "1357", "6233", "9692",
            "8623", "6112", "2065", "4238",
            "9753", "3453", "7641", "9642",
            "9674", "5213", "6860", "8661",
            "9865",
    };

    final static String[] codes_EN = {
            "1082", "1081", "1080", "1079",
            "1077", "1083", "2000", "1069",
            "1078", "1073", "1084", "1068",
            "4124", "3783", "2355", "2344",
            "1067", "1090", "1076", "1072",
            "1086", "1087", "1088", "1089",
            "1075", "1066"
    };
    final static String[] codes_en = {
            "1092", "1106", "1105", "1104", "1103",
            "1093", "1107", "1069", "1111", "1102",
            "1094", "1097", "1070", "1071", "2001",
            "1096", "1195", "1113", "1112", "1072",
            "1110", "1109", "1098", "1099", "1100",
            "1066"
    };
    public final static String[] integers = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "0",
    };
    final static String[] intCodes = {
            "AadGB", "chgxsADASfD", "ejsdxvVZXhF", "hsdXZGyh", "KHFgDsGasdl",
            "dfsan", "hdsfbBSV", "afxczhsdr", "Surwct", "uhSDFUW"
    };

    public static String EncodeString(String text) {
        String result = text;
        for (int i = 0; i < RU.length; i++) {
            result = result.replaceAll(ru[i], codes_ru[i]);
            result = result.replaceAll(RU[i], codes_RU[i]);
            if (i < en.length) {
                result = result.replaceAll(en[i], codes_en[i]);
                result = result.replaceAll(EN[i], codes_EN[i]);
            }
            result = result.replaceAll(space, "4444").replace((String.valueOf(quote)), "7653");
        }

        return result;
    }

    public static String DecodeString(String text) {
        try {
            String result = text;
            for (int i = 0; i < RU.length; i++) {
                result = result.replaceAll(codes_ru[i], ru[i]);
                result = result.replaceAll(codes_RU[i], RU[i]);
                if (i < en.length) {
                    result = result.replaceAll(codes_en[i], en[i]);
                    result = result.replaceAll(codes_EN[i], EN[i]);
                }
                result = result.replaceAll("4444", space).replace('-', quote);
            }
            return result;
        } catch (NullPointerException ignored) {
            String result = String.valueOf(DecodeInt(text));
            for (int i = 0; i < RU.length; i++) {
                result = result.replaceAll(codes_ru[i], ru[i]);
                result = result.replaceAll(codes_RU[i], RU[i]);
                if (i < en.length) {
                    result = result.replaceAll(codes_en[i], en[i]);
                    result = result.replaceAll(codes_EN[i], EN[i]);
                }
                result = result.replaceAll("4444", space).replace('-', quote);
            }
            return result;
        }
    }

    public static String EncodeInt(int integer) {
        String result = String.valueOf(integer);
        for (int i = 0; i < integers.length; i++)
            result = result.replaceAll(integers[i], intCodes[i]);
        return result;
    }

    public static Integer DecodeInt(String integer) {
        String result = String.valueOf(integer);
        for (int i = 0; i < integers.length; i++)
            result = result.replaceAll(intCodes[i], integers[i]);
        return Integer.valueOf(result);
    }

    public static String EncodeFloat(float Float) {
        String result = String.valueOf(Float);
        for (int i = 0; i < integers.length; i++) {
            result = result.replaceAll(integers[i], intCodes[i]);
        }
        return result;
    }

    public static Float DecodeFloat(String Float) {
        String result = Float;
        for (int i = 0; i < integers.length; i++)
            result = result.replaceAll(intCodes[i], integers[i]);
        return java.lang.Float.valueOf(result);
    }
}
