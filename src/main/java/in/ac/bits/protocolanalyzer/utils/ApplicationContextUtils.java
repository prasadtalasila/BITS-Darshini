package in.ac.bits.protocolanalyzer.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        context = ctx;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
