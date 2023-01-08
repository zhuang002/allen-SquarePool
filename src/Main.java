import java.util.Scanner;

public class Main {

	static int n;
	static int t;
	static Position[] trees = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		t = sc.nextInt();
		trees = new Position[t];
		for (int i=0;i<t;i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			trees[i] = new Position(x-1,y-1);
		}
		
		int squareWidth = 0;
		// complexity: O(n^3*t)
		for (int x=0;x<n;x++) { // repeat n times
			for (int y=0;y<n;y++) { // repeat n times
				int maxX = n-x;
				int maxY = n-y;
				int maxWidth;
				if (maxX>maxY) {
					maxWidth = maxY;
				} else {
					maxWidth = maxX;
				}
				for (int width=maxWidth;width>=0;width--) { // repeat n times
					if (!containsTree(x,y, width)) { // repeat t times
						if (squareWidth<width) {
							squareWidth = width;
						}
					}
				}
			}
		}
		System.out.println(squareWidth);
	}
	
	private static boolean containsTree(int x, int y, int width) {
		// TODO Auto-generated method stub
		for (Position pos:trees) { // repeat t times
			if (x<=pos.x && x+width-1>=pos.x && y<=pos.y && y+width-1>=pos.y) {
				return true;
			}
		}
		return false;
	}

}

class Position {
	int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}