package omok;

import lombok.Data;

@Data
public class OmokCheck {
	
	int[][] board;
	private int finish;
	
	OmokCheck(int[][] board) {
		this.board = board;
		this.finish = 0;
	}
	public void resetBoard() {
		for(int i = 0; i < 19; i++)
		{
			for(int j = 0; j < 19; j++)
			{
				board[i][j] = 0;
			}
		}
	}
	
	public int isFinish(int x, int y, int stone) {
		if (isRowOmok(x, y, board, stone) ||
				isColOmok(x, y, board, stone) || 
				isRightUpOmok(x, y, board, stone) ||
				isLeftUpOmok(x, y, board, stone)) 
			{
				return 1; // 게임끝
			}
		else {
			return 0; // 게임 그대로 진행
		}
	}

	public static boolean isRowOmok(int x, int y, int[][] board, int stone) {
		int cnt = 0; // 돌 갯수
		int nextX = x; // 좌표 초기값
		int nextY = y;

		// 세로 검사
		while (true) {
			nextX++; // x값 증가시키면서 검사
			if (nextX >= 0 && nextX < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 탈출해서 아래 검사
					break;
				}
			} else {
				break;
			}
		}
		nextX = x; // 초기 좌표값으로 돌리기
		while (true) {
			nextX--; // x값 감소시키면서 검사
			if (nextX >= 0 && nextX < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		return isFive(cnt);
	}

	public static boolean isColOmok(int x, int y, int[][] board, int stone) {

		int cnt = 0; // 돌 갯수
		int nextX = x; // 좌표 초기값
		int nextY = y;

		// 가로 검사
		while (true) {
			nextY++; // y값 증가시키면서 검사
			if (nextY >= 0 && nextY < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		nextY = y;

		while (true) {
			nextY--; // y값 감소시키면서 검사
			if (nextY >= 0 && nextY < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		return isFive(cnt);
	}

	// 대각선 검사
	public static boolean isRightUpOmok(int x, int y, int[][] board, int stone) {

		int cnt = 0; // 돌 갯수
		int nextX = x; // 좌표 초기값
		int nextY = y;
		while (true) {
			nextX++;
			nextY++; 
			if (nextX >= 0 && nextX < 19 && nextY >= 0 && nextY < 19) { 
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		nextX = x;
		nextY = y;
		while (true) {
			nextX--;
			nextY--; 
			if (nextX >= 0 && nextX < 19 && nextY >= 0 && nextY < 19) {
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		return isFive(cnt);
	}

	// 대각선 검사
	public static boolean isLeftUpOmok(int x, int y, int[][] board, int stone) {
		int cnt = 0; // 돌 갯수
		int nextX = x; // 좌표 초기값
		int nextY = y;
		while (true) {
			nextX += 1;
			nextY -= 1; // x값 증가시키면서 검사
			if (nextX >= 0 && nextX < 19 && nextY >= 0 && nextY < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		nextX = x;
		nextY = y;
		while (true) {
			nextX -= 1;
			nextY += 1; // x값 증가시키면서 검사
			if (nextX >= 0 && nextX < 19 && nextY >= 0 && nextY < 19) { // 오목판 안 벗어나게 범위지정
				if (stone == board[nextX][nextY]) {
					cnt++; // 만약 움직인 곳에 동일한 돌이 있으면 cnt 증가
				} else { // 아니면 반복문 나가기
					break;
				}
			} else {
				break;
			}
		}
		return isFive(cnt);
	}

	public static boolean isFive(int cnt) {
		if (cnt == 4) { // 만약에 오목 되면 승리
			System.out.println();
			System.out.println("승리");
			return true;
		} else {
			return false;
		}
	}
	
}