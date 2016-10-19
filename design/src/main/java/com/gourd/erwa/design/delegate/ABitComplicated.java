package com.gourd.erwa.design.delegate;

interface I {

    void f();

    void g();
}

/**
 * 通过使用接口，委托可以做到类型安全并且更加灵活。在这个例子里，类别C可以委托类别A或类别B，类别C拥有方法使自己可以在类别A或类别B间选择。
 * 因为类别A或类别B必须实现接口I规定的方法，所以在这里委托是类型安全的。
 * 这个例子显示出委托的缺点是需要更多的代码。
 *
 * @author wei.Li
 */
public class ABitComplicated {
}

class A implements I {
    public void f() {
        System.out.println("A: doing f()");
    }

    public void g() {
        System.out.println("A: doing g()");
    }
}

class B implements I {
    public void f() {
        System.out.println("B: doing f()");
    }

    public void g() {
        System.out.println("B: doing g()");
    }
}

class C implements I {
    // delegation
    private I i = new A();

    public void f() {
        i.f();
    }

    public void g() {
        i.g();
    }

    // normal attributes
    public void toA() {
        i = new A();
    }

    void toB() {
        i = new B();
    }
}


class MainABitComplicated {
    public static void main(String[] args) {
        C c = new C();
        c.f();     // output: A: doing f()
        c.g();     // output: A: doing g()
        c.toB();
        c.f();     // output: B: doing f()
        c.g();     // output: B: doing g()
    }
}
