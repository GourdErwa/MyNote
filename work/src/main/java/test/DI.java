/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wei.Li by 15/1/8 (gourderwa@163.com).
 */
public class DI {

    public static final int NUM = 20;
    public static final String AA = "track_54ad07bb77c889951f0ef21614207182161420718816[{facility=0, _ret_code.mid=cc86f9a5-8687-418f-b4d6-deb9558813fc}]";
    public static int TEMP = 0;
    static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static boolean aBoolean() {

        System.out.println("==========" + TEMP);

        if (TEMP < NUM && TEMP % 2 == 0) {
            TEMP++;
            return aBoolean();
        }
        System.out.println("===========>");
        return false;
    }

    public static void validateKey(String key) {
        Matcher m = p.matcher(AA);
        key = m.replaceAll("");
        byte[] keyBytes = key.getBytes();
       /* if (keyBytes.length > MemcachedClientIF.MAX_KEY_LENGTH) {
            throw new IllegalArgumentException("Key is too long (maxlen = "
                    + MemcachedClientIF.MAX_KEY_LENGTH + ")");
        }*/
        if (keyBytes.length == 0) {
            throw new IllegalArgumentException(
                    "Key must contain at least one character.");
        }
        // Validate the key
        for (byte b : keyBytes) {
            if (b == ' ' || b == '\n' || b == '\r' || b == 0) {
                throw new IllegalArgumentException(
                        "Key contains invalid characters:  ``" + key + "''");
            }
        }
    }

    public static void main(String[] args) {
        aBoolean();
        validateKey(AA);
        Matcher m = p.matcher(AA);
        System.out.println(m.replaceAll(""));
    }
}
