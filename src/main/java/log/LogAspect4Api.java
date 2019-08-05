package log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class LogAspect4Api {

    public void around(ProceedingJoinPoint point){
        String clazzName = point.getTarget().getClass().getName();       //获取类名
        String methodName = point.getSignature().getName();             //获取方法名
//        String[] paramsNames = ParamsParseUtil.getParamsName(this.getClass(), clazzName, methodName);   //得到方法参数的名称
//        String paramsContent = ParamsParseUtil.paramsInfo(paramsNames, point);                           //得到参数值
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求的URI
        String uri = request.getRequestURI();
        //设置菜单
        System.out.println("AOP拦截请求的URI为 = "+uri+",类名："+clazzName+",方法："+methodName);
    }
}
