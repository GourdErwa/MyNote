package guice.ex2;

import com.google.inject.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Controller 依赖多个 service 的实现
 * service 依赖 dao 的实现
 *
 * @author wei.Li
 */
interface Controller {
    void invoke();
}

/**
 * 对于标注为 @NeedBindingCustom 的对象需要进行注入
 * {@link ServiceModule#configure(Binder)}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
@interface AddImpl01 {
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
@interface AddImpl02 {
}


interface AddService {
    void invoke();
}

interface SubService {
    void invoke();
}

/**
 * 在实现你确定MyService定义的对象，就要被注射为MyServiceImpl而不是其它的实现类的话，
 * 可以在MyService接口加上@ImplementedBy(MyServiceImpl.class)
 * 这样的话，在MyModule里的configure方法中就可以不加任何东西，容器就会自动注射给MyServiceImpl对象。
 */
@ImplementedBy(MulService01Impl.class)
interface MulService {
    void invoke();
}

@ImplementedBy(Dao01Impl.class)
interface Dao {
    void invoke();
}

class ControllerImpl implements Controller {

    @Inject
    @AddImpl01
    private AddService addService01;
    @Inject
    @AddImpl02
    private AddService addService02;

    @Inject
    private SubService subService;
    @Inject
    private MulService mulService;

    @Override
    public void invoke() {

        addService01.invoke();
        addService02.invoke();
        subService.invoke();
        mulService.invoke();
    }

}

/**
 * Singleton 标识为单例模式
 */
@Singleton
class AddService01Impl implements AddService {

    @Inject
    private Dao dao;

    @Override
    public void invoke() {
        dao.invoke();
        final String s = this.getClass().getSimpleName() + "\tinvoke";
        System.out.println(s);
    }

}

class AddService02Impl implements AddService {

    @Override
    public void invoke() {
        final String s = this.getClass().getSimpleName() + "\tinvoke";
        System.out.println(s);
    }

}

class SubService01Impl implements SubService {

    @Inject
    private Dao dao;

    @Override
    public void invoke() {
        dao.invoke();
        final String s = this.getClass().getSimpleName() + "\tinvoke";
        System.out.println(s);
    }

}

class MulService01Impl implements MulService {

    @Inject
    private Dao dao;

    @Override
    public void invoke() {
        dao.invoke();
        final String s = this.getClass().getSimpleName() + "\tinvoke";
        System.out.println(s);
    }

}

class Dao01Impl implements Dao {

    @Override
    public void invoke() {
        final String s = this.getClass().getSimpleName() + "\tinvoke";
        System.out.println(s);
    }

}


class ThirdParty {

    private Controller controller;

    @Inject
    public void setController(Controller controller) {
        this.controller = controller;
    }

    void work() {
        this.controller.invoke();
    }

}

class ControllerModule implements Module {

    public void configure(Binder binder) {
        // 运行时动态的将MyServiceImpl对象赋给MyService定义的对象，而且对象是单例的。
        binder.bind(Controller.class).to(ControllerImpl.class).in(Scopes.SINGLETON);
    }

}

class ServiceModule implements Module {

    public void configure(Binder binder) {
        binder.bind(AddService.class).annotatedWith(AddImpl01.class).to(AddService01Impl.class);
        binder.bind(AddService.class).annotatedWith(AddImpl02.class).to(AddService02Impl.class);

        binder.bind(SubService.class).to(SubService01Impl.class);
    }

}

class Test {

    public static void main(String[] args) {

        // 定义注射规则
        ControllerModule controllerModule = new ControllerModule();
        ServiceModule serviceModule = new ServiceModule();

        // 根据注射规则，生成注射者
        Injector injector = Guice.createInjector(controllerModule, serviceModule);

        // 注射者将需要注射的bean,按照规则,把client这个客户端进行注射
        ThirdParty thirdParty = new ThirdParty();
        injector.injectMembers(thirdParty);

        thirdParty.work();
    }

}
