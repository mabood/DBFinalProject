package guimain;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageCache {
    static HashMap<String, Image> images = new HashMap<>();

    public static void fetchImageTimeout(final String imgUrl, long millis) {
        final Object lock = new Object();
        new Thread(new Runnable() {

            @Override
            public void run() {
                fetchImage(imgUrl);
                synchronized (lock) {
                    lock.notify();
                }
            }
        }).start();
        synchronized (lock) {
            try {
                // Wait for specific millis and release the lock.
                // If blockingMethod is done during waiting time, it will wake
                // me up and give me the lock, and I will finish directly.
                // Otherwise, when the waiting time is over and the
                // blockingMethod is still
                // running, I will reacquire the lock and finish.
                lock.wait(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static Image fetchImage(String url) {

        if (url != null) {
            if (images.containsKey(url)) {
                return images.get(url);
            } else {
                return images.put(url, new Image(url));
            }
        } else {
            return null;
        }
    }
}
