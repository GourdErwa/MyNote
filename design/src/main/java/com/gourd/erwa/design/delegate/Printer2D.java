package com.gourd.erwa.design.delegate;


/**
 * 委派模式示例:
 *
 * @author wei.Li
 */
interface Print {

    void print();

}


class Printer2D implements Print {

    @Override
    public void print() {
        System.out.print("PrinterReal...");
    }

}

class Printer3D implements Print {

    // delegation
    private Print delegate;

    public Printer3D(Print delegate) {
        this.delegate = delegate;
    }


    Print delegate() {

        return delegate;
    }

    /**
     * 真实的打印工作 委派其他打印机实现
     */
    @Override
    public void print() {
        System.out.println("3D 建模中...");
        delegate.print();
    }
}

