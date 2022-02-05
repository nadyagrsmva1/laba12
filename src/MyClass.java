import java.util.Arrays;
import java.lang.Thread;

public class MyClass {
    static final int SIZE = 10000000;
    static final int HALFSIZE = SIZE / 2;

    public static void singleThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] + Math.sin(0.2f + i / 5) + Math.cos(0.2f + i / 5) + Math.cos(0.2f + i / 2));
        }
        System.out.println("Время работы первого метода");
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void main(String[] args) {
        singleThread();
        doubleThread();
    }

    public static void doubleThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        float[] a1 = new float[HALFSIZE];
        float[] a2 = new float[HALFSIZE];
        long a = System.currentTimeMillis();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALFSIZE; i++)
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 5));
            }
        });


        t1.run();
        for (int i = 0; i < HALFSIZE; i++) {
            int j = i + HALFSIZE;
            a2[i] = (float) (a2[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 5));
        }
        synchronized (t1) {
            System.arraycopy(a1, 0, arr, 0, HALFSIZE);
            System.arraycopy(a2, 0, arr, HALFSIZE, HALFSIZE);
            System.out.println("Время работы второго метода: " + (System.currentTimeMillis() - a));
        }

    }
}