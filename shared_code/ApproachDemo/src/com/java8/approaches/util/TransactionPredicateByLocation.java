package com.java8.approaches.util;

public class TransactionPredicateByLocation 
	implements TransactionPredicate {

	@Override
	public boolean test(Transaction t) {
		return "PUNE".equals(t.getLocation());
	}

}
