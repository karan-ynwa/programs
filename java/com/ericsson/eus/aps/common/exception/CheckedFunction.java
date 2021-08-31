package com.ericsson.eus.aps.common.exception;

@FunctionalInterface
public interface CheckedFunction<T,R> {
   public R apply(T t) throws AutopayDaoException;
}
