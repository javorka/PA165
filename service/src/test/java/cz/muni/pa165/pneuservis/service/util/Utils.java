package cz.muni.pa165.pneuservis.service.util;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * Created by peter on 11/25/16.
 */
public class Utils {
    public static final Object unwrapProxy(Object bean) throws Exception {

 /*
  * If the given object is a proxy, set the return value as the object
  * being proxied, otherwise return the given object.
  */
        if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {

            Advised advised = (Advised) bean;

            bean = advised.getTargetSource().getTarget();
        }

        return bean;
    }

}
