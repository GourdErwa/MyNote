package com.gourd.erwa.concurrent.mypool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lw
 * Date: 14-4-22
 */
public class Task_Demo extends Task {
    public String str;

    public static void main(String[] args) throws Exception {
        Task_Demo task_demo = new Task_Demo();
        ThreadPool.getInstance().batchAddTask(task_demo.taskCore());
        //task_demo.run();
    }

    /**
     * 所有任务的核心 所有特别的业务逻辑执行之处-demo
     *
     * @throws Exception Exception
     */
    @Override
    public Task[] taskCore() throws Exception {
        Task_Demo task;
        System.out.println("工作队列..初始化......");
        Task[] tasks = new Task[100];


        for (int i = tasks.length - 1; i >= 0; i--) {

            task = new Task_Demo();
            List<String> list = new ArrayList<String>();
            list.add(i + "");
            task.taksModel = list;
            task.str = "00000_" + i;
            tasks[i] = task;
        }
        return tasks;
    }

    /**
     * 是否用到数据库
     *
     * @return 是否用到数据库
     */
    @Override
    protected boolean useDb() {
        return false;
    }

    /**
     * 是否需要立即执行
     *
     * @return 是否需要立即执行
     */
    @Override
    protected boolean needExecuteImmediate() {
        return false;
    }

    /**
     * 任务信息
     *
     * @return String
     */
    @Override
    public String info() {
        return null;
    }

    /**
     * 重写run（）
     */
    @Override
    public void run() {

        try {
            System.out.println(str);
            System.out.println();
            System.out.println("任务模拟数据：" + this.taksModel.toString());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
