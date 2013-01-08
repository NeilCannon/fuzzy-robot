package org.fuzzy_robot.bus;

/**
 * User: neil
 * Date: 07/11/2012
 */
public interface Subscriber<T> {
    void receive(T value);
}
