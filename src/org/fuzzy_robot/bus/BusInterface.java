package org.fuzzy_robot.bus;

/**
 * User: neil
 * Date: 19/11/2012
 */
public interface BusInterface {

    <T> void subscribe(Class<T> clazz, Subscriber<T> subscriber);
    <T> void subscribe(Class<T> clazz, String channelId, Subscriber<T> subscriber);

    /**
     * Subscribe to T1 & T2: the Susbriber's receive() method will not be called until both types are available
     */
    <T1, T2> void subscribe(Class<T1> clazz1, Class<T2> clazz2, Subscriber2<T1, T2> subscriber);

    <T> void publish(Class<T> clazz, Provider<T> provider);
    <T> void publish(Class<T> clazz, String channelId, Provider<T> provider);
    void publish(Channel channel, Object value);

    <T> void request(Class<T> clazz, String channelId, String[] params);

    <T> void subscribeAndRequest(Class<T> clazz, Subscriber<T> subscriber, String[] params);

    void unSubscribe(Subscriber subscriber);

}
