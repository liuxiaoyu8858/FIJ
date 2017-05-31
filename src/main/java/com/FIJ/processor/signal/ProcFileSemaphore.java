package com.FIJ.processor.signal;

import java.io.File;

/**
 * @Author liuxiaoyu
 * @Date 17/5/31.
 */
public class ProcFileSemaphore implements ProcSemaphore {
    @Override
    public boolean isExit() {
        File file = new File("/tmp/PANAMA_SIG_EXIT");
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
