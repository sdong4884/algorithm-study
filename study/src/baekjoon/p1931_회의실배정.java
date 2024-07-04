package baekjoon;

import java.io.*;
import java.util.*;

public class p1931_회의실배정 {
	static class Meeting implements Comparable<Meeting> {
		int start, end;

		public Meeting(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public int compareTo(Meeting o) {
			// 종료시간이 빠른 순으로, 종료시간이 같으면 시작시간이 빠른 순으로 
			return end == o.end ? start - o.start : end - o.end;
		}
	}
	
	static int N;
	static Meeting[] list; // ArrayList로 선언했을 때보다 조금 더 빠른듯 

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		list = new Meeting[N];
		StringTokenizer st;
		int s, e;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			list[i] = new Meeting(s, e);
		}
		
		Arrays.sort(list);
		
		int cnt = 0;
		// 가장 빨리 끝나는 회의를 우선으로 탐욕선택하여 회의 배정 
		int prevEnd = 0;
		for (Meeting next : list) {
			if (prevEnd <= next.start) {
				cnt++;
				prevEnd = next.end;
			}
		}
		
		System.out.println(cnt);
	}
}
