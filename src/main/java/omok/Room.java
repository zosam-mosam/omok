package omok;

import lombok.Data;

@Data
public class Room {
	

		public int roomId;
		//돌 선택
		private String black;
		private String white;
		
		private int turn=1;
		
}
