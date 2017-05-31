package com.FIJ.processor;

import com.FIJ.processor.signal.ProcFileSemaphore;
import com.FIJ.processor.signal.ProcSemaphore;
import com.FIJ.processor.signal.ProcFileSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author liuxiaoyu
 * @Date 17/5/27.
 */
public abstract class AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);

    abstract protected void init();

    abstract protected void execute(String bean);

    abstract protected long sleepInMillis();

    protected void run(String bean) {
        ProcSemaphore semaphore = new ProcFileSemaphore();
        if (semaphore.isExit()) {
            logger.info("MainProcessor receive exit signal.");
            System.exit(1);
        }

        init();
        try {
            while (true) {
                if (semaphore.isExit()) {
                    logger.info("MainProcessor receive exit signal.");
                    System.exit(0);
                }
                execute(bean);
                sleep();
            }
        } catch (Exception e) {
            logger.error("MainProcessor exception - ", e);
            System.exit(1);
        }
    }

    private void sleep() {
        try {
            long sleepInMillis = sleepInMillis();
            logger.info("sleep {}...", sleepInMillis / 1000);
            Thread.sleep(sleepInMillis);
        } catch (InterruptedException e) {
            logger.error("sleep exception - ", e);
        }
    }
}
