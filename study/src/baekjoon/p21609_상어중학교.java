package baekjoon;

import java.io.*;
import java.util.*;

public class p21609_상어중학교 {
	static class Block implements Comparable<Block> {
		int x, y;
		
		public Block(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Block o) {
			// 기준블록 정렬: 일반블록 중 행의 번호가 가장 작은 블록, 그러한 블록이 여러개면 열의 번호가 가장 작은 블록
			return x == o.x ? y - o.y : x - o.x;
		}
	}
	
	static class Group implements Comparable<Group> {
		int x, y; // 블록그룹의 기준 행과 열 
		int cnt, rainbowCnt; // 전체블록 개수, 무지개블록 개수 
		ArrayList<Block> blocks; // 블록그룹에 속한 블록들 
		
		public Group(int x, int y, int cnt, int rainbowCnt, ArrayList<Block> blocks) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
			this.rainbowCnt = rainbowCnt;
			this.blocks = blocks;
		}

		@Override
		public int compareTo(Group o) {
			// 그룹 정렬: 전체 블록 개수 > 무지개블록 개수 > 기준블록의 행이 가장 큰 것 > 기준블록의 열이 가장 큰 것 순
			if (cnt == o.cnt) {
				if (rainbowCnt == o.rainbowCnt) {
					if (x == o.x) {
						return o.y - y;
					} else {
						return o.x - x;
					}
				} else {
					return o.rainbowCnt - rainbowCnt;
				}
			} else {
				return o.cnt - cnt;
			}
		}
	}
	
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;
	static int[][] map;
	static boolean[][] visited;
	static ArrayList<Group> blockGroup;
	static int score;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 검은색블록 -1, 무지개블록 0, 일반블록 1 ~ M
  			}
		}
		
		/* 
		 블록그룹: 2개이상 연결된 블록의 집합 (일반블록 하나 이상, 일반블록 색은 모두 같아야 함, 검은색 블록X, 무지개 블록O) 
		 - 기준 블록: 일반블록 중 행의 번호가 가장 작은 블록, 그러한 블록이 여러개면 열의 번호가 가장 작은 블록
		 
		 1. 블록그룹을 찾는다. (크기 > 무지개블록 개수 > 기준블록의 행이 가장 큰 것 > 기준블록의 열이 가장 큰 것 순)
		 2. 1에서 찾은 블록 그룹의 모든 블록을 제거. 블록 그룹에 포함된 블록의 수를 B라고 했을 때, B^2점을 획득.
		 3. 격자에 중력이 작용.
		 4. 격자가 90도 반시계 방향으로 회전.
		 5. 다시 격자에 중력이 작용.
		 */
		
		
		// 조건에 맞는 블록그룹을 모두 찾는다.
		blockGroup = new ArrayList<>();
		getBlockGroup();
		
		while (blockGroup.size() > 0) {
			// 저장된 블록그룹들 중에서 조건에 맞는 블록그룹 하나를 찾아 블록제거, 점수획득!
			Collections.sort(blockGroup);
			Group selectedGroup = blockGroup.get(0);
			for (Block b : selectedGroup.blocks) {
				map[b.x][b.y] = -2;
			}
			score += (selectedGroup.cnt * selectedGroup.cnt);
			
			// 중력 - 회전 - 중력 
			gravity();
			rotate();
			gravity();
			
			blockGroup = new ArrayList<>();
			getBlockGroup();
		}
		
		System.out.println(score);
	}

	private static void getBlockGroup() {
		// 블록그룹: 2개이상 연결된 블록의 집합 (일반블록 하나 이상, 일반블록 색은 모두 같아야 함, 검은색 블록X, 무지개 블록O)
		visited = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0 && !visited[i][j]) {
					bfs(i, j, map[i][j]);
				}
			}
		}
	}

	private static void bfs(int i, int j, int color) {
		Queue<Block> q = new LinkedList<>();
		q.add(new Block(i, j));
		visited[i][j] = true;
		
		ArrayList<Block> blocks = new ArrayList<>(); // 모든 블록 저장 
		ArrayList<Block> rainbow = new ArrayList<>(); // 무지개 블록만 따로 한 번 더 저장 
		blocks.add(new Block(i, j));
		
		while (!q.isEmpty()) {
			Block now = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int x = now.x + dx[k];
				int y = now.y + dy[k];
				
				if (x < 0 || y < 0 || x >= N || y >= N || visited[x][y] || map[x][y] == -1 || map[x][y] == -2
						|| (map[x][y] > 0 && map[x][y] != color)) {
					continue;
				}
				
				q.add(new Block(x, y));
				visited[x][y] = true;
				blocks.add(new Block(x, y));
				
				if (map[x][y] == 0) {
					rainbow.add(new Block(x, y));
				}
			}
		}
		
		// 블록의 개수가 2개 미만이면 블록그룹X, 방문기록 초기화 후 패스 
		if (blocks.size() < 2) {
			for (Block b : blocks) {
				visited[b.x][b.y] = false;
			}
			return;
		}
		// 무지개블록은 다른 그룹에도 속할 수 있으므로, 다음 탐색을 위해 방문기록 초기화 
		if (rainbow.size() > 0) {
			for (Block b : rainbow) {
				visited[b.x][b.y] = false;
			}
		}
		
		// 기준블록 찾기 
		int generalX = 0;
		int generalY = 0;
		Collections.sort(blocks);
		for (Block b : blocks) {
			if (map[b.x][b.y] > 0) {
				generalX = b.x;
				generalY = b.y;
				break;
			}
		}
		
		// 조건에 맞는 블록그룹 저장 
		blockGroup.add(new Group(generalX, generalY, blocks.size(), rainbow.size(), blocks));
	}
	
	private static void gravity() {
		// 격자에 중력 작용: 검은색 블록을 제외한 모든 블록이 다른 블록이나 격자의 경계를 만나기 전까지 행의 번호가 큰 칸으로 이동 
		for (int i = N - 2; i >= 0; i--) {
			for (int j = 0; j < N; j++) {
				
				int idx = i + 1;
				if (idx < N && map[idx][j] == -2) {
					idx++;
				}
				
				if (idx != i + 1) {
					map[idx - 1][j] = map[i][j];
					map[i][j] = -2;
				}
			}
		}
	}
	
	private static void rotate() {
		// 격자가 90도 반시계 방향으로 회전 
		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				temp[i][j] = map[N - 1 - j][i];
			}
		}
		map = temp;
	}
}
