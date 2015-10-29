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

/**
 * @author wei.Li by 15/3/17 (gourderwa@163.com).
 */
var a = 10;
console.log(a);

function fn() {
    console.log(a);
    if (!a) {
        var a = 20;
    }

    try {
        $.error("aa");
    } catch (e) {
        console.log("catch");

    }
    console.log(a);
}

fn();


