package baekjoon;

import java.io.*;
import java.util.*;

public class p1700_멀티탭스케줄링 {
	static int N, K;
	static ArrayList<Integer> list;
	static HashSet<Integer> set;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		list = new ArrayList<>();
		set = new HashSet<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < K; i++) {
			list.add(Integer.parseInt(st.nextToken()));
		}
		
		int count = 0;
		
		for (int i = 0; i < K; i++) {
			int now = list.get(i);
			
			// 사용하려는 전기 용품의 콘센트가 이미 꽂혀있는 경우
			if (set.contains(now)) {
				continue;
			}
			
			// 사용하려는 전기 용품의 콘센트가 꽂혀있지 않은 경우, 빈 멀티탭 구멍이 있다면 바로 꼽으면 끝 
			if (set.size() < N) {
				set.add(now);
				continue;
			}
			
			// 사용하려는 전기 용품의 콘센트가 꽂혀있지 않고 멀티탭도 꽉 찬 경우 
			List after = list.subList(i + 1, K);
			int max = -1;
			int product = 0;
			for (int s : set) {
				int tmp = 0;
				// 이후에 사용되지 않는 전기 용품이 있다면 그걸 뺀다 
				if (!after.contains(s)) {
					tmp = K;
				}
				// 모두 이후에 사용 된다면 가장 나중에 사용되는 전기 용품을 뺀다 
				else {
					tmp = after.indexOf(s);
				}
				
				if (tmp > max) {
					max = tmp;
					product = s;
				}
			}
			
			set.remove(product);
			set.add(now);
			count++;
		}
		
		System.out.println(count);
	}

}
