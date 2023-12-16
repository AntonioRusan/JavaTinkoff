package edu.hw10;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxy implements InvocationHandler {
    private Object target;
    private Map<String, Object> cache;
    private final static Logger LOGGER = LogManager.getLogger();

    private CacheProxy(Object target) {
        this.target = target;
        this.cache = new HashMap<>();
    }

    public static <T> T create(T target, Class<? extends T> targetClass) {
        Class[] interfaces = targetClass.getInterfaces();
        return (T) Proxy.newProxyInstance(
            targetClass.getClassLoader(),
            interfaces,
            new CacheProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodKey = method.getName() + Arrays.toString(args);

        if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).persist()) {
            if (cache.containsKey(methodKey)) {
                var cachedValue = cache.get(methodKey);
                LOGGER.info("Method: " + methodKey + " cache already contains value: " + cachedValue);
                return cachedValue;
            } else {
                Object result = method.invoke(target, args);
                cache.put(methodKey, result);
                return result;
            }
        } else {
            return method.invoke(target, args);
        }
    }

    public Map<String, Object> getCache() {
        return cache;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Cache {
        boolean persist();
    }
}
