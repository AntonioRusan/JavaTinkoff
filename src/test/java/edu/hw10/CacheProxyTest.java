package edu.hw10;

import java.lang.reflect.Proxy;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheProxyTest {
    public interface FibCalculator {
        @CacheProxy.Cache(persist = true)
        public long fib(int number);
    }

    public class FibCalculatorImpl implements FibCalculator {
        @Override
        public long fib(int number) {
            long first = 1;
            long second = 1;
            if (number == 1 || number == 2) {
                return first;
            } else if (number > 2) {
                long current = 2;
                for (int i = 3; i <= number; i++) {
                    current = first + second;
                    second = first;
                    first = current;
                }
                return current;
            } else {
                return -1;
            }
        }
    }

    public interface FibCalculatorWithoutCaching {
        @CacheProxy.Cache(persist = false)
        public long fib(int number);
    }

    public class FibCalculatorWithoutCachingImpl implements FibCalculatorWithoutCaching {
        @Override
        public long fib(int number) {
            long first = 1;
            long second = 1;
            if (number == 1 || number == 2) {
                return first;
            } else if (number > 2) {
                long current = 2;
                for (int i = 3; i <= number; i++) {
                    current = first + second;
                    second = first;
                    first = current;
                }
                return current;
            } else {
                return -1;
            }
        }
    }

    @Test
    void testFibCalculatorWithCache() {
        FibCalculator fibCalculator = new FibCalculatorImpl();
        FibCalculator proxy = CacheProxy.create(fibCalculator, fibCalculator.getClass());

        long result1 = proxy.fib(10);
        long result2 = proxy.fib(10);
        long result3 = proxy.fib(1);

        Object cacheProxy = Proxy.getInvocationHandler(proxy);
        Map<String, Object> cacheFromProxy = ((CacheProxy) cacheProxy).getCache();

        assertThat(result1).isEqualTo(55);
        assertFalse(cacheFromProxy.isEmpty());
        assertThat(result2).isEqualTo(result1);
        assertThat(result3).isEqualTo(1);
    }

    @Test
    void testFibCalculatorWithoutCache() {
        FibCalculatorWithoutCaching fibCalculator = new FibCalculatorWithoutCachingImpl();
        FibCalculatorWithoutCaching proxy = CacheProxy.create(fibCalculator, fibCalculator.getClass());

        long result1 = proxy.fib(10);
        long result2 = proxy.fib(10);
        long result3 = proxy.fib(1);

        Object cacheProxy = Proxy.getInvocationHandler(proxy);
        Map<String, Object> cacheFromProxy = ((CacheProxy) cacheProxy).getCache();

        assertThat(result1).isEqualTo(55);
        assertTrue(cacheFromProxy.isEmpty());
        assertThat(result2).isEqualTo(result1);
        assertThat(result3).isEqualTo(1);
    }

}
