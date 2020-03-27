public class TowThread {
    private static final Object lock = new Object();
    public static void main(String[] args) {
        final int[] flag = {0};
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 100; i+=2) {
                synchronized (lock){
                    if(i != 1){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                    lock.notifyAll();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int j = 2; j <= 100; j+=2) {
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(j);
                    lock.notifyAll();
                }
            }
        });
        t2.start();
        t1.start();
    }
}
