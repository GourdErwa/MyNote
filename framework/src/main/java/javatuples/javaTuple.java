package javatuples;

/*
Unit（1个元素）
Pair<a,b>（2个元素）
Triplet<a,b,c>（3个元素）
Quartet<a,b,c,d>（4个元素）
Quintet<a,b,c,d,e>（5个元素）
Sextet<a,b,c,d,e,f>（6个元素）
Septet<a,b,c,d,e,f,g>（7个元素）
Octet<a,b,c,d,e,f,g,h>（8个元素）
Ennead<a,b,c,d,e,f,g,h,i>（9个元素）
Decade<a,b,c,d,e,f,g,h,i,j>（10个元素）
 */

import org.javatuples.*;

/**
 * Typesafe
 * Immutable
 * Iterable
 * Serializable
 * Comparable (implements Comparable<Tuple>)
 * Implementing equals(...) and hashCode()
 * Implementing toString()
 * <p>
 * Tuple 各个实现类X元组 方法说明
 * <p>
 * 提供 with()/fromArray()/fromCollection() 静态方法创建对应数量的元组
 * 提供getValueX() 获取 x 位置对应数据值
 * 提供当前元组 addAtX(X x)、add(Tuple t) 转换为数量更大的元组
 * 提供当前元组 removeFromX() 转换为数量低的元组
 *
 * @author wei.Li
 */
public class javaTuple {

    public static void main(String[] args) {

        final String t1 = "t1";
        final Long t2 = 2L;
        final Double t3 = 3D;
        final Short t4 = 4;
        final Boolean t0 = true;

        // 1元组
        Unit<String> unit = new Unit<>("GourdErwa");
        // 2元组
        Pair<String, Object> pair = Pair.with("GourdErwa", 9527);
        // 3元组
        Triplet<String, Object, Object> triplet = Triplet.with("GourdErwa", 9527, 1.0);
        //...
        KeyValue<String, Object> keyValue = KeyValue.with("GourdErwa", "9527");
        LabelValue<String, Object> labelValue = LabelValue.with("GourdErwa", "9527");

        //4元组
        final Quartet<String, Long, Double, Short> quartet = Quartet.with(t1, t2, t3, t4);
        //4元组 转换为 5元组
        final Quintet<Boolean, String, Long, Double, Short> quintet = quartet.addAt0(t0);
        System.out.println(quintet);//[true, t1, 2, 3.0, 4]
        //4元组个转换为3元组
        final Quartet<Boolean, String, Long, Short> removeFrom3 = quintet.removeFrom3();
        System.out.println(removeFrom3);//[true, t1, 2, 4]

    }
}
