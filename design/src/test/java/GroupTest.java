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
 * @author wei.Li by 15/3/27 (gourderwa@163.com).
 */
public class GroupTest {
    public GooseGroup wings;
}

/**
 * 大雁群
 */
class GooseGroup {
    public Goose goose;

    public GooseGroup(Goose goose) {
        this.goose = goose;
    }
}

/**
 * 大雁
 */
class Goose {
    public Wings wings;

    public Goose() {
        wings = new Wings();
    }
}

/**
 * 翅膀
 */
class Wings {
}

