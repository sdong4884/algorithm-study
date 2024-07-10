package baekjoon;

import java.io.*;
import java.util.*;

public class p9935_문자열폭발 {

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		String boomb = br.readLine();
		
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < str.length(); i++) {
			stack.push(str.charAt(i));
			if (stack.size() >= boomb.length()) {
				boolean flag = true;
				for (int j = 0; j < boomb.length(); j++) {
					if (stack.get(stack.size() - boomb.length() + j) != boomb.charAt(j)) {
						flag = false; // stack에 아직 폭발 문자열 없음 !
					}
				}
				// stack에 폭발 문자열 있다면 폭발 시키기  
				if (flag) {
					for (int j = 0; j < boomb.length(); j++) {
						stack.pop();
					}
				}
			}
		}
		
		if (stack.size() == 0) {
			System.out.println("FRULA");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for (Character i : stack) {
			sb.append(i);
		}
		System.out.println(sb);
	}

}
