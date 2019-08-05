package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 代理的示例demo
 */
public class MyCglibProxy {


    /**
     * Hello类
     */
    static class Hello{
        public void sayHello() {
            System.out.println("Hello world!!");
        }
    }

    static class HelloMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("------插入前置通知代码-------------");
            //执行相应的目标方法
            Object rs = methodProxy.invokeSuper(o,objects);
            System.out.println("------插入后置处理代码-------------");

            return rs;
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        //代理类基础自 被代理类Hello
        enhancer.setSuperclass(Hello.class);
        //设置回调类，代理逻辑在回调类中
        enhancer.setCallback(new HelloMethodInterceptor());

        //生成代理类
        Hello hello = (Hello) enhancer.create();
        hello.sayHello();
    }

}