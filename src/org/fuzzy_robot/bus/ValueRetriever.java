package org.fuzzy_robot.bus;

/**
 * User: neil
 * Date: 16/11/2012
 */
public interface ValueRetriever<T> {
    T retrieveValue(String[] params) throws Exception;

}
