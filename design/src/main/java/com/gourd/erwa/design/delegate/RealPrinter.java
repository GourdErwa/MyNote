package com.gourd.erwa.design.delegate;

/**
 * 在这个例子里，类模拟打印机Printer拥有针式打印机RealPrinter的实例，Printer拥有的方法print()将处理转交给RealPrinter的方法print()。
 *
 * @author wei.Li
 */
class RealPrinter { // the "delegate"
    void print() {
        System.out.print("something");
    }
}

class Printer { // the "Delegator"
    private RealPrinter p = new RealPrinter(); // create the delegate

    void print() {
        p.print(); // delegation
    }
}

class Main {
    // to the outside world it looks like Printer actually prints.
    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.print();
    }
}
