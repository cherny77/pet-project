package cracker;

import java.util.concurrent.ThreadFactory;

public class CaughtExceptionsThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println("Exception in executorService:");
				e.printStackTrace();
			}
		});
		return t;
	}
}