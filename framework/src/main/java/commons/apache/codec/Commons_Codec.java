package commons.apache.codec;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-4-18
 */
public class Commons_Codec {

    private static final String STRPTNAME = "http://xxx.do?ptname=我是中国人";


    /**
     * Base64 编解码
     * <p>
     * 将 bytecode 转换成 ascii 码
     */
    public static void base64() {


        Base64 base64 = new Base64();

        System.out.println("原字符：" + STRPTNAME);

        String encodeStr = new String(base64.encode(STRPTNAME.getBytes()));
        System.out.println("编码后：" + encodeStr);

        String decodeStr = new String(base64.decode(encodeStr.getBytes()));
        System.out.println("解码后：" + decodeStr);

    }

    /**
     * Hex 编解码
     * <p>
     * 通常我们会对于 URL Form GET 时候进行 16 进位编码, 将 byte[] 转成 char[]
     */
    public static void hex() {

        Hex hex = new Hex();
        String STRPTNAME = "中国";
        System.out.println("原字符：" + STRPTNAME);

        String encodeStr = new String(hex.encodeHex(STRPTNAME.getBytes()));
        System.out.println("编码后：" + encodeStr);

        try {
            String decodeStr = new String(hex.decode(encodeStr.getBytes()));
            System.out.println("解码后：" + decodeStr);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * md5 加密
     */
    public static void md5() {

        Hex hex = new Hex();
        System.out.println("原字符：" + STRPTNAME);
        String md5Str = new String(DigestUtils.md5Hex(STRPTNAME.getBytes()));
        System.out.println("加密后：" + md5Str);
    }


    public static void java_Codec() {

        try {

            String strPtname_1 = new String(STRPTNAME.getBytes("UTF-8"), "ISO-8859-1");

            System.out.println("转码前：" + strPtname_1);
            String strPtname_2 = new String(strPtname_1.getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("编码后：" + strPtname_2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
