package baekjoon;

import java.io.*;
import java.util.*;

public class p17143_낚시왕 {
	static int R, C, M;
	static Shark[][] map;
	static int result = 0;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("input.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken()); // 상어의 수 
		
		map = new Shark[R + 1][C + 1];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			map[r][c] = new Shark(r, c, s, d, z);
		}
		
		// 1. 낚시왕이 오른쪽으로 한 칸 이동한다. 
		for (int y = 1; y <= C; y++) {
			
			// 2. 낚시왕이 있는 열에 있는 상어 중에서 땅과 제일 가까운 상어를 잡는다. 상어를 잡으면 격자판에서 잡은 상어가 사라진다.
			for (int x = 1; x <= R; x++) {
				if (map[x][y] != null) {
					result += map[x][y].z;
					map[x][y] = null;
					break;
				}
			}
			
			// 3. 상어가 이동한다.
			moveShark();
		}
		
		System.out.println(result);
	}

	private static void moveShark() {
		// 현재 map에 있는 상어들을 큐에 넣기 
		Queue<Shark> q = new LinkedList<>();
		for (int x = 1; x <= R; x++) {
			for (int y = 1; y <= C; y++) {
				if (map[x][y] != null) {
					q.add(map[x][y]);
				}
			}
		}
		
		// map에서 상어 이동 시키기 
		map = new Shark[R + 1][C + 1];
		while (!q.isEmpty()) {
			Shark shark = q.poll();
			
			// 상어의 이동 최적화 
			int speed = shark.s;
			if (shark.d == 1 || shark.d == 2) {
				speed %= (R - 1) * 2;
			} else {
				speed %= (C - 1) * 2;
			}
			
			// 상어 방향에 따라 이동
			for (int s = 0; s < speed; s++) {
				if (shark.d == 1) { // 위 
					if(shark.r == 1){ 
						shark.d = 2; 
						shark.r++;
					} else {
						shark.r--;
					}
	            } else if (shark.d == 2) { // 아래 
	            	if(shark.r == R){
	                      shark.d = 1;
	                      shark.r--;
	                  } else {
	                	  shark.r++;
	                  }
	            } else if (shark.d == 3) { // 오른쪽 
	            	if(shark.c == C){
	                      shark.d = 4;
	                      shark.c--;
	                  } else {
	                	  shark.c++;
	                  }
	            } else if (shark.d == 4) { // 왼쪽 
	            	if(shark.c == 1){
	                      shark.d = 3;
	                      shark.c++;
	                  } else {
	                	  shark.c--;
	                  }
	            }
			}
			
			if (map[shark.r][shark.c] != null) {
				if (map[shark.r][shark.c].z < shark.z) {
					map[shark.r][shark.c] = shark;
				}
			} else {
				map[shark.r][shark.c] = shark;
			}
		}
	}

	static class Shark {
		int r, c; // (r, c)는 상어의 위치
		int s; // 속력 
		int d; // 이동방향 (1-위, 2-아래, 3-오른쪽, 4-왼쪽)
		int z; // 크기 

		public Shark (int r, int c, int s, int d, int z) {
			this.r = r;
			this.c = c;
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}
}
