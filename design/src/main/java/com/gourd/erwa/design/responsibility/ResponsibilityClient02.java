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

package com.gourd.erwa.design.responsibility;

import java.util.ArrayList;
import java.util.List;

/*
责任链模式
 */

/**
 * 过滤器接口
 */
interface Filter {

    void doFilter(Request request, Response response, FilterChain chain);

}

/**
 * 过滤器链
 *
 * @author wei.Li by 15/3/30 (gourderwa@163.com).
 */
class ResponsibilityClient02 {


    public static void main(String[] args) {

        Request request = new Request("请求信息>.<  &nbsp; 混蛋，呵呵");
        Response response = new Response("响应信息");

        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new HTMLFilter());
        filterChain.addFilter(new FaceFilter());
        filterChain.addFilter(new SensitiveFilter());

        filterChain.doFilter(request, response, filterChain);


        System.out.println(request);
        System.out.println(response);
    }


}

/**
 * 请求信息
 */
class Request {

    private String requestStr;

    Request(String requestStr) {
        this.requestStr = requestStr;
    }

    String getRequestStr() {
        return requestStr;
    }

    void setRequestStr(String requestStr) {
        this.requestStr = requestStr;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestStr='" + requestStr + '\'' +
                '}';
    }
}

/**
 * 响应信息
 */
class Response {

    private String responseStr;

    Response(String responseStr) {
        this.responseStr = responseStr;
    }

    String getResponseStr() {
        return responseStr;
    }

    void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseStr='" + responseStr + '\'' +
                '}';
    }
}

/**
 * 过滤器链
 */
class FilterChain implements Filter {

    private List<Filter> filters = new ArrayList<>();
    private int index = 0;

    FilterChain addFilter(Filter f) {
        this.filters.add(f);
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        if (index == filters.size()) {
            return;
        }

        System.out.println("FilterChain doFilter run ...");
        Filter filter = filters.get(index++);
        filter.doFilter(request, response, chain);
    }
}

/**
 * html 标签过滤器
 */
class HTMLFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {

        final String requestStr = request.getRequestStr().replace("&nbsp;", "空格符号");
        request.setRequestStr(requestStr);

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + "--------HTMLFilter");

    }

}

/**
 * 敏感字过滤
 */
class SensitiveFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {

        final String requestStr = request.getRequestStr().replaceAll("[混蛋|呵呵]+", "敏感字");

        request.setRequestStr(requestStr);

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + "--------SesitiveFilter");

    }

}

/**
 * 表情符过滤器
 */
class FaceFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {

        final String requestStr = request.getRequestStr().replace(">.<", "(*^__^*)");
        request.setRequestStr(requestStr);

        chain.doFilter(request, response, chain);

        response.setResponseStr(response.getResponseStr() + "--------FaceFilter");
    }

}
