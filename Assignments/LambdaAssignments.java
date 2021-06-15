package com.java8.lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@FunctionalInterface
interface MathOperation {
	int perform(int x, int y);
}

public class LambdaAssignments {

	public static void main(String[] args) {
		MathOperation addOpr = (int x, int y)->x + y;		
		MathOperation subtractOpr = (int x, int y)->x - y;
		addOpr.perform(10, 40);
		subtractOpr.perform(35, 15);
		
		Predicate<Order> predicateAmount = 
				(Order order)->order.getPrice()<=10000;
		Predicate<Order> predicateStatus = 
				(Order order)->!order.getLocation().equals("Pune");
		Predicate<Order> combinedPredicate = predicateAmount.and(predicateStatus);
		List<Order> orders = OrderList.getOrders();
		orders.removeIf(combinedPredicate);



		orders.forEach(System.out::println);
		//OR
		//orders.forEach((Order order)->System.out.println(order));

		Function<Integer, Integer> func = (Integer x)->x * x;
		func.apply(5);

		List<String> words = new ArrayList<String>();
		words.add("abc");
		words.add("abcd");
		words.add("abcde");
		words.add("abcdef");
		words.removeIf((String word)->word.length()%2!=0);

		StringBuilder sb = new StringBuilder();
		Consumer<String> consumer = 
			(String word)->sb.append(word.charAt(0));
		words.forEach(consumer);
		System.out.println("All words: " + sb.toString());

		UnaryOperator<String> unaryOperator = 
			(String word)->word.toUpperCase();
		words.replaceAll(unaryOperator);

		StringBuilder sb_2 = new StringBuilder();
		Map<String, String> map = new HashMap<>();
		map.put("A", "abc"); //entry 1 - Map.Entry - getKey() & getValue()
		map.put("B", "bcd"); //entry 2
		map.put("C", "cde"); //entry 3
		Set<Entry<String, String>> entries = map.entrySet();
		entries.forEach(
		(Entry<String, String> entry) -> sb_2.append(entry.getKey() + " - " + entry.getValue()));

		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(25);
		numbers.add(44);
		numbers.add(12);
		Runnable runnable = 
		()->numbers.forEach(System.out::println);
		Thread thread = new Thread(runnable);
		thread.start();
	}

}













