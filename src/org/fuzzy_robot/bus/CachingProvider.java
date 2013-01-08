package org.fuzzy_robot.bus;

/**
 * User: neil
 * Date: 10/11/2012
 */
public class CachingProvider<T> implements Provider<T> {

    private final Cacher<String, T> cacher;
    private final ParameterisedProvider<T> delegate;

    private int hits;
    private int accesses;

    /* Cache implementation to use
     */
    interface Cacher<K,V> {
        V get(K key);
        V put(K key, V value);
    }

    /* Default cache implementation
     */
    public static class LruCacher<K,V> implements Cacher<K,V> {
        //private final LruCache<K, V> lruCache;

        LruCacher(int size) {
            //lruCache = new LruCache<K, V>(size);
        }

        @Override
        public V get(K key) {
            return null;//lruCache.get(key);
        }

        @Override
        public V put(K key, V value) {
           return null;//lruCache.put(key, value);
        }
    }

    public CachingProvider(ParameterisedProvider<T> delegate, int size) {
        this(delegate, new LruCacher<String, T>(size));
    }

    public CachingProvider(ParameterisedProvider<T> delegate, Cacher<String, T> cacher) {
        this.cacher = cacher;
        this.delegate = delegate;
    }

    // todo incomplete
    public void provide(final Subscriber<T> subscriber, final String[] params) {
//        accesses++;
//        T value = cacher.get(param);
//        if (value == null) {
//            delegate.provide(new Subscriber<T>() {
//                public void receive(T value) {
//                    cacher.put(param, value);
//                    subscriber.receive(value);
//                }
//            }, param);
//        }
//        else {
//            hits++;
//            subscriber.receive(value);
//        }
    }

    public int getHits() {
        return hits;
    }

    public int getAccesses() {
        return accesses;
    }
}
