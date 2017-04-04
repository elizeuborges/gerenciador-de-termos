package com.github.elizeuborges;

import java.util.function.Function;

public class Execute {
	
	public static <T, R> Function<T, R> unchecked(CheckedFunction<T, R> consumer){
		return (t) -> {
			try {
				return consumer.apply(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
	
}