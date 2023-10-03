package cn.afternode.csi.utils;

public class ThreadRunner {
    private final Thread thread;

    public ThreadRunner(String name, Runnable runnable) {
        this.thread = new Thread(runnable);
        thread.setName(name);
    }

    public Thread getThread() {
        return thread;
    }

    public void run() {
        thread.start();
    }
}
