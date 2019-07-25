package com.iot.smart.water.meter.util;

import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static Map<String, Integer> token2idMap = new HashMap<>();
    private static Map<Integer, String> id2tokenMap = new HashMap<>();

    private static final String TOKEN_FOR_ADMIN_TEST = "QQQWWWEEE";
    private static final Integer ADMIN_ID_FOR_TEST = 11;
    private static final String TOKEN_FOR_MEMBER_TEST = "MEMBERQQQWWW";
    private static final Integer MEMBER_ID_FOR_TEST = 13;

    public static String createToken(Integer id) {
        String originToken = id2tokenMap.remove(id);
        if (originToken != null) {
            token2idMap.remove(originToken);
        }
        String token = HashUtil.MD5.get(id.toString() + System.currentTimeMillis());
        id2tokenMap.put(id, token);
        token2idMap.put(token, id);
        return token;
    }


    public static Integer getId(String token) {
        if (TOKEN_FOR_ADMIN_TEST.equals(token)) {
            return ADMIN_ID_FOR_TEST;
        }
        if (TOKEN_FOR_MEMBER_TEST.equals(token)) {
            return MEMBER_ID_FOR_TEST;
        }

        return token2idMap.get(token);
    }

    public static void clear(Integer id, String token) {
        id2tokenMap.remove(id);
        token2idMap.remove(token);
    }
}
