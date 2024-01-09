package baekjoon;

import java.io.*;
import java.util.*;

public class p13458_시험감독 {
	static int[] classroom;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		classroom = new int[N];
		for (int i = 0; i < N; i++) {
			classroom[i] = Integer.parseInt(st.nextToken());
		}
		
		st = new StringTokenizer(br.readLine());
		int B = Integer.parseInt(st.nextToken()); // 총감독관이 감시할 수 있는 응시자의 수 
		int C = Integer.parseInt(st.nextToken()); // 부감독관이 감시할 수 있는 응시자의 수 
		
		// ** count 정의시 int 아닌 long으로 정의해야함 ..! 
		long count = 0;
		for (int i = 0; i < N; i++) {
			// 총감독관
			classroom[i] -= B;
			count++;
			if (classroom[i] <= 0) {
				continue;
			}
			
			// 부감독관
			count += classroom[i] / C;
			if (classroom[i] % C > 0) {
				count++;
			}
		}
		System.out.println(count);
	}

}
