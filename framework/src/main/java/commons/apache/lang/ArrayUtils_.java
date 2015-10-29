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

package commons.apache.lang;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author wei.Li by 14/9/30.
 */
public class ArrayUtils_ {


    private static int[] INTS = {1, 2, 3, 4, 5};

    private static void simleExample() {

        INTS = ArrayUtils.add(INTS, 0, 6); // 6添加到第0个位置
        ArrayUtils.contains(INTS, 6); //true
        ArrayUtils.indexOf(INTS, 6);//0
        ArrayUtils.isEmpty(INTS); //return array == null || array.length == 0;
        ArrayUtils.isNotEmpty(INTS);//array != null && array.length != 0
        ArrayUtils.isSameLength(INTS, INTS);
        ArrayUtils.lastIndexOf(INTS, 4);

        // if (array == null || array.length == 0) {return EMPTY_INT_ARRAY;}
        ArrayUtils.nullToEmpty(INTS);

        ArrayUtils.remove(INTS, 0);//按下标移除
        ArrayUtils.removeElement(INTS, 4);//remove 第一个找到的元素
        ArrayUtils.reverse(INTS);//反转
        ArrayUtils.subarray(INTS, 0, 100);//截取
        ArrayUtils.toObject(INTS);//Integer[]
        ArrayUtils.toPrimitive(ArrayUtils.toObject(INTS), 1);//替换 所有的null为 1

        int i = ArrayUtils.INDEX_NOT_FOUND;// -1
        //new Byte[0]
        final Byte[] emptyByteObjectArray = ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY;


    }

    private static void print() {
        for (int anInt : INTS) {
            System.out.print(anInt + "\t");
        }
    }

    public static void main(String[] args) {
        simleExample();
    }
}
