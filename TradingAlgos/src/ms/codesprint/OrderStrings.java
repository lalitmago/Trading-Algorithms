package ms.codesprint;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderStrings {

	enum comparison_type {
		numeric, lexicographical
	}

	static String findResult(List<String[]> lst, int key, boolean reversed, comparison_type cmpType) {
		/*
		 * Map<Integer, String> map = (HashMap<Integer, String>) lst.stream()
		 * .collect(Collectors.toMap(strArray_i -> lst.indexOf(strArray_i),
		 * strArray_j -> strArray_j[key - 1], (x, y) -> x, LinkedHashMap::new));
		 */

		/*
		 * Map<Integer, Integer> map = lst.stream()
		 * .collect(Collectors.toMap(strArray_i -> lst.indexOf(strArray_i),
		 * strArray_j -> Integer.parseInt(strArray_j[key - 1]), (x, y) -> x,
		 * LinkedHashMap::new));
		 */
		/*
		 * for(int itr: map.keySet()) { System.out.println(itr + "=" +
		 * map.get(itr)); for(int i = 0; i < map.get(itr).length; i++)
		 * System.out.print(map.get(itr)[i] + " "); System.out.println(); }
		 */
		
		if (cmpType.equals(comparison_type.numeric)) {
			Map<Integer, Integer> map = lst.stream()
					.collect(Collectors.toMap(strArray_i -> lst.indexOf(strArray_i),
							strArray_j -> Integer.parseInt(strArray_j[key - 1]), (x, y) -> x, LinkedHashMap::new))
					.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));

			map.keySet();
			
			
		} else {

		}
		return "ruk ja";
	}

	public static void main(String[] args) {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT.
		 * Your class should be named Solution.
		 */

		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		// System.out.println("n read");

		in.nextLine();

		// String str;

		List<String> s = new ArrayList<String>();

		for (int a_i = 0; a_i < n; a_i++) {
			// str =
			s.add(in.nextLine());

		}

		List<String[]> lstStringsIntoColumnArrays = s.stream().map(s_i -> s_i.split(" ")).collect(Collectors.toList());

		int key = in.nextInt();
		boolean reversed = in.nextBoolean();

		comparison_type input = comparison_type.valueOf(in.next());

		String result = findResult(lstStringsIntoColumnArrays, key, reversed, input);
		System.out.println(result);

		in.close();

	}
}