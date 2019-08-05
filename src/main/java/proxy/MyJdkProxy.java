package proxy;


import java.lang.reflect.*;

/**
 * jdk 代理的示例demo
 */
public class MyJdkProxy{

    /**
     * 接口
     */
    public interface IHello{
        void sayHello();
    }

    /**
     * IHello接口实现类
     */
    static class Hello implements IHello{
        public void sayHello() {
            System.out.println("Hello world!!");
        }
    }

    /**
     * 自定义InvocationHandler
     */
    static  class HWInvocationHandler implements InvocationHandler {
        //目标对象
        private Object target;
        public HWInvocationHandler(Object target){
            this.target = target;
        }
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("------插入前置通知代码-------------");
            //执行相应的目标方法
            Object rs = method.invoke(target,args);
            System.out.println("------插入后置处理代码-------------");
            return rs;
        }
    }
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        /*
         * 方式一：
         */
        //获取动态代理类class并加载
        Class proxyClazz = Proxy.getProxyClass(IHello.class.getClassLoader(),IHello.class);
        //获得代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        //通过构造函数来创建动态代理对象，将自定义的InvocationHandler实例传入
        IHello iHello = (IHello) constructor.newInstance(new HWInvocationHandler(new Hello()));

        /*
         * 方式二：可看成方式一的语法糖
         */
//        IHello  iHello = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(),  //加载接口的类加载器
//                new Class[]{IHello.class},      //一组接口
//                new HWInvocationHandler(new Hello())); //自定义的InvocationHandler


        //通过代理对象调用目标方法
        iHello.sayHello();
    }
}