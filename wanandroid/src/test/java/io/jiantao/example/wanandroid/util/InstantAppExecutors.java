package io.jiantao.example.wanandroid.util;

import java.util.concurrent.Executor;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/4/20
 */
public class InstantAppExecutors extends AppExecutors {
    private static final Executor sExecutor = new Executor() {
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    };

    public InstantAppExecutors() {
        super(sExecutor, sExecutor, sExecutor);
    }
}
