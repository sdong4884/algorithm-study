package baekjoon;

import java.io.*;
import java.util.*;

public class p16235_나무재태크 {
	static class Tree implements Comparable<Tree> {
		int x, y, age;
		
		public Tree(int x, int y, int age) {
			this.x = x;
			this.y = y;
			this.age = age;
		}

		@Override
		public int compareTo(Tree o) {
			return age - o.age;
		}
	}

	static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };
	static int N, M, K;
	static int[][] map, arr;
	static Queue<Tree> tree;
	static Queue<Tree> deadTree;
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		arr = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = 5;
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		tree = new PriorityQueue<>();
		deadTree = new PriorityQueue<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int age = Integer.parseInt(st.nextToken());
			tree.add(new Tree(x, y, age));
		}
		
		// K년이 지난 후 상도의 땅에 살아있는 나무의 개수
		for (int k = 0; k < K; k++) {
			spring();
			summer();
			fall();
			winter();
		}
		
		System.out.println(tree.size());
	}

	private static void spring() {
		// 봄에는 나무가 자신의 나이만큼 현재 칸에 있는 양분을 먹고, 나이가 1 증가한다.
		// 하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다.
		// 만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
		Queue<Tree> temp = new PriorityQueue<>();
		while (!tree.isEmpty()) {
			Tree now = tree.poll();
			
			if (map[now.x][now.y] >= now.age) {
				map[now.x][now.y] -= now.age;
				temp.add(new Tree(now.x, now.y, now.age + 1));
			} else {
				deadTree.add(now);
			}
		}
		tree = new PriorityQueue<>(temp);
	}
	
	private static void summer() {
		// 여름에는 봄에 죽은 나무가 양분으로 변한다.
		// 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가되고 소수점 아래는 버린다.
		while (!deadTree.isEmpty()) {
			Tree now = deadTree.poll();
			map[now.x][now.y] += now.age / 2;
		}
	}
	
	private static void fall() {
		// 가을에는 나무가 번식한다. 번식하는 나무는 나이가 5의 배수이어야 하며, 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
		Queue<Tree> temp = new PriorityQueue<>();
		while (!tree.isEmpty()) {
			Tree now = tree.poll();
			
			if (now.age % 5 == 0) {
				for (int k = 0; k < 8; k++) {
					int x = now.x + dx[k];
					int y = now.y + dy[k];
					
					if (x >= 0 && y >= 0 && x < N && y < N) {
						temp.add(new Tree(x, y, 1));
					}
				}
			}
			
			temp.add(now);
		}
		tree = new PriorityQueue<>(temp);
	}
	
	private static void winter() {
		// S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] += arr[i][j];
			}
		}
	}
}
