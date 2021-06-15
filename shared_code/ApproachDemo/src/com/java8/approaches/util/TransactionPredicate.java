package com.java8.approaches.util;

@FunctionalInterface
public interface TransactionPredicate { //Functional interface
	boolean test(Transaction t);
}

