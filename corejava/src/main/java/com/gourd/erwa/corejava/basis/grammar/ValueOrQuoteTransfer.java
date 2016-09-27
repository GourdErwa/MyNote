/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * Blog  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 */

package com.gourd.erwa.util.corejava.basis.grammar;


import java.util.UUID;

/**
 * JAVA 值传递 or 引用传递 example
 *
 * @author wei.Li by 14-9-3.
 */
public class ValueOrQuoteTransfer {

    public int id;
    public String name;

    public ValueOrQuoteTransfer() {
    }

    public ValueOrQuoteTransfer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return 初始化一个对象
     */
    private static ValueOrQuoteTransfer init() {
        return new ValueOrQuoteTransfer(1, "init");
    }

    /**
     * 参数传入对象，只修改他的值，不改变引用
     *
     * @param transfer 测试对象
     */
    private static void updateObjectValue(ValueOrQuoteTransfer transfer) {
        transfer.name = "updateObjectValue";
    }

    /**
     * 参数传入对象，只改变引用
     *
     * @param transfer 测试对象
     */
    private static void updateObjectTransfer(ValueOrQuoteTransfer transfer) {

        transfer = new ValueOrQuoteTransfer(2, "TWO");
    }

    /**
     * Thread 0.5s 打印一次传入的对象 transfer
     *
     * @param transfer 测试对象
     */
    private static void updateObject2Thread(ValueOrQuoteTransfer transfer) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500L);
                        System.out.println(transfer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {

        /**
         * 参数传入对象，只修改他的值，不改变引用 test 结果
         *
         * updateObjectValue before    is  :ValueOrQuoteTransfer{id=1, name='init'}
         * updateObjectValue after     is  :ValueOrQuoteTransfer{id=1, name='updateObjectValue'}
         */
        ValueOrQuoteTransfer transfer = init();
        System.out.println("updateObjectValue before    is  :" + transfer);
        updateObjectValue(transfer);
        System.out.println("updateObjectValue after     is  :" + transfer);

        /**
         * 参数传入对象，只改变引用 test
         *
         * updateObjectTransfer before is   :ValueOrQuoteTransfer{id=1, name='init'}
         * updateObjectTransfer after  is   :ValueOrQuoteTransfer{id=1, name='init'}
         */
        ValueOrQuoteTransfer transfer1 = init();
        System.out.println("updateObjectTransfer before is   :" + transfer1);
        updateObjectTransfer(transfer1);
        System.out.println("updateObjectTransfer after  is   :" + transfer1);

        /**
         * 外部一直修改实参对象的值  1s 修改一次
         * 方法内部一直打印传入的实参对象 0.5s 打印一次
         *
         *ValueOrQuoteTransfer{id=1, name='init'}
         ValueOrQuoteTransfer{id=1, name='init'}
         ValueOrQuoteTransfer{id=1, name='99aade6b-cfc5-44d6-8ac7-66394493aaa9'}
         ValueOrQuoteTransfer{id=1, name='99aade6b-cfc5-44d6-8ac7-66394493aaa9'}
         ValueOrQuoteTransfer{id=1, name='79b71dc2-aa78-41ed-a54b-4d7b2fcfee04'}
         ValueOrQuoteTransfer{id=1, name='79b71dc2-aa78-41ed-a54b-4d7b2fcfee04'}
         ValueOrQuoteTransfer{id=1, name='16b5db8d-0376-4403-9d59-3252a6c00660'}
         ValueOrQuoteTransfer{id=1, name='16b5db8d-0376-4403-9d59-3252a6c00660'}
         ValueOrQuoteTransfer{id=1, name='abbdf6af-ee4a-4013-a541-a40351af5001'}
         ValueOrQuoteTransfer{id=1, name='abbdf6af-ee4a-4013-a541-a40351af5001'}
         ValueOrQuoteTransfer{id=1, name='f150d849-5313-476d-bae9-b4ea77e60601'}
         ValueOrQuoteTransfer{id=1, name='f150d849-5313-476d-bae9-b4ea77e60601'}
         ValueOrQuoteTransfer{id=1, name='6b76e3f3-785c-4893-a8c8-969fe613ec81'}
         ValueOrQuoteTransfer{id=1, name='6b76e3f3-785c-4893-a8c8-969fe613ec81'}
         ValueOrQuoteTransfer{id=1, name='499c4ce7-9333-4ba1-8f73-e1125c4e3957'}
         ValueOrQuoteTransfer{id=1, name='499c4ce7-9333-4ba1-8f73-e1125c4e3957'}
         ValueOrQuoteTransfer{id=1, name='c0fc60cd-2190-4777-a892-9fbd37b98c80'}
         ValueOrQuoteTransfer{id=1, name='c0fc60cd-2190-4777-a892-9fbd37b98c80'}
         ValueOrQuoteTransfer{id=1, name='e9bf9acb-d494-40af-a1ca-f1e313afcbf5'}
         ValueOrQuoteTransfer{id=1, name='e9bf9acb-d494-40af-a1ca-f1e313afcbf5'}
         ValueOrQuoteTransfer{id=1, name='56e281fc-7cd2-493f-85f1-6f937ca836dc'}
         ValueOrQuoteTransfer{id=1, name='56e281fc-7cd2-493f-85f1-6f937ca836dc'}
         ValueOrQuoteTransfer{id=1, name='2817fe8d-5f7e-4989-a8ef-dfccec1acc54'}
         ValueOrQuoteTransfer{id=1, name='2817fe8d-5f7e-4989-a8ef-dfccec1acc54'}
         ValueOrQuoteTransfer{id=1, name='82a0b98e-0be6-48ad-ad6a-174d40025979'}
         ValueOrQuoteTransfer{id=1, name='82a0b98e-0be6-48ad-ad6a-174d40025979'}
         ValueOrQuoteTransfer{id=1, name='55292141-503c-4f2e-a7fe-3a5483196e41'}
         ValueOrQuoteTransfer{id=1, name='55292141-503c-4f2e-a7fe-3a5483196e41'}
         ValueOrQuoteTransfer{id=1, name='cb8c2570-e565-4f5d-8109-ad84fcb0421a'}
         ValueOrQuoteTransfer{id=1, name='cb8c2570-e565-4f5d-8109-ad84fcb0421a'}
         ValueOrQuoteTransfer{id=1, name='e575ba44-a22f-4ba3-9e00-e17d9173c397'}
         ValueOrQuoteTransfer{id=1, name='e575ba44-a22f-4ba3-9e00-e17d9173c397'}

         */
        ValueOrQuoteTransfer transfer2 = init();
        updateObject2Thread(transfer2);//方法内部起一线程一直打印 引用对象[transfer2] 的值
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000L);
                        transfer2.name = UUID.randomUUID().toString();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ValueOrQuoteTransfer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
