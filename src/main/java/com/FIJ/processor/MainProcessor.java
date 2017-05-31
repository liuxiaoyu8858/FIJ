package com.FIJ.processor;

import com.FIJ.cron.CronTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author liuxiaoyu
 * @Date 17/5/31.
 */
public class MainProcessor implements AbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MainProcessor.class);

    private ApplicationContext context = null;

    @Override
    protected void init() {
        context = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" });
    }

    @Override
    protected void execute(String bean) {
        Object obj = context.getBean(bean);
        logger.info("start cron task:{}", obj);
        if (obj instanceof CronTask) {
            ((CronTask) obj).execute();
            logger.info("cron task {} execute end.\n", bean);
            return;
        }
        logger.warn("{} not instance of CronTask interface.", bean);
    }

    @Override
    protected long sleepInMillis() {
        return 0;
    }
    public static void main(String[] args) {
        if (args.length < 1) {
           // usage();
            System.exit(1);
        }
        MainProcessor processor = new MainProcessor();
        processor.run(args[0]);
    }
}
