package com.github.elizeuborges;

@FunctionalInterface
public interface CheckedFunction<T, R> {
	R apply(T t) throws Exception;
}