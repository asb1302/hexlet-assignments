// Класс для самостоятельной работы

package exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> annotatedBeans = new HashMap<>();
    private Map<String, String> loggingLevels = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String level = bean.getClass().getAnnotation(Inspect.class).level();

            annotatedBeans.put(beanName, bean.getClass());
            loggingLevels.put(beanName, level);
        }

        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (!annotatedBeans.containsKey(beanName)) {
            return bean;
        }
        Class beanClass = annotatedBeans.get(beanName);
        String level = loggingLevels.get(beanName);

        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                    String message = String.format(
                            "Was called method: %s() with arguments: %s",
                            method.getName(),
                            Arrays.toString(args)
                    );

                    if (level.equals("info")) {
                        LOGGER.info(message);
                    } else {
                        LOGGER.debug(message);
                    }

                    return method.invoke(bean, args);
                }
        );
    }
}
// END
