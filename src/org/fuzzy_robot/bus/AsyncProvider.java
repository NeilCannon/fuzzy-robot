package org.fuzzy_robot.bus;

import android.os.AsyncTask;
import android.util.Log;
import com.panasonic.inflight.BuildConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: neil
 * Date: 14/11/2012
 */
public abstract class AsyncProvider<T> implements Provider<T>, ValueRetriever<T> {
    private static final String TAG = AsyncProvider.class.getSimpleName();

    private boolean needsParams = false;
    private String[] lastParams;
    private T value;
    private AsyncTask<Void, Void, T> task;
    private Map<Subscriber<T>, Void> receivers = new HashMap<Subscriber<T>, Void>();

    protected AsyncProvider() {
    }

    protected AsyncProvider(boolean needsParams) {
        this.needsParams = needsParams;
    }

    public synchronized void provide(Subscriber<T> subscriber, String[] params) {
        Log.d(TAG, "provide(");
        if (!Arrays.equals(params, lastParams)) {
            value = null;
        }
        if (value != null) {
            subscriber.receive(value);
        }
        if (task != null) {
            Log.w(TAG, "Duplicate Subscriber");
        }
        if (!needsParams || params != null) {
            receivers.put(subscriber, null);
            createTask(params);
        }
        else {
            // waiting for params
            return;
        }
    }

    protected synchronized AsyncTask createTask(final String[] params) {
        Log.d(TAG, "createTask(");
        task = new AsyncTask<Void, Void, T>() {

            protected T doInBackground(Void... ignored) {
                try {
                    value = retrieveValue(params);
                    lastParams = params;
                } catch (Exception e) {
                    e.printStackTrace();
                    if (BuildConfig.DEBUG) {
                        throw new RuntimeException(e);
                    }
                }
                return value;
            }

            protected void onPostExecute(T value) {
                Log.d(TAG, "onPostExecute(" + value);
                if (value != null) {
                    synchronized (AsyncProvider.this) {
                        for (Subscriber<T> subscriber : receivers.keySet()) {
                            subscriber.receive(value);
                        }
                        receivers.clear();
                        task = null;
                    }
                }
            }

        };
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        return task;
    }

    public abstract T retrieveValue(String[] params) throws Exception;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AsyncProvider");
        sb.append("{needsParams=").append(needsParams);
        sb.append(", lastParams=").append(lastParams == null ? "null" : Arrays.asList(lastParams).toString());
        sb.append(", value=").append(value);
        sb.append(", task=").append(task);
        sb.append(", receivers=").append(receivers);
        sb.append('}');
        return sb.toString();
    }
}
