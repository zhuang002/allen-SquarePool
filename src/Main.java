import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static int n;
	static int t;
	static ArrayList<Position> xtrees = new ArrayList<>();
	static ArrayList<Position> ytrees = new ArrayList<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		t = sc.nextInt();
		
		for (int i=0;i<t;i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			Position tree = new Position(x-1,y-1);
			xtrees.add(tree);
			ytrees.add(tree);
		}
		
		Collections.sort(xtrees, (a,b)->a.x-b.x);
		Collections.sort(ytrees, (a,b)->a.y-b.y);
		
		Rect rect = new Rect(0,0,n,n);
		rect = getMaxSquareSize(rect, xtrees, ytrees);
		System.out.println(rect.squareSize());
	}
	
	private static Rect getMaxSquareSize(Rect rect, ArrayList<Position> xt, ArrayList<Position> yt) {
		// TODO Auto-generated method stub
		if (xt.isEmpty()) {
			return rect;
		}
		RemoveReturnValue rv = removeATree(rect, xt, yt);
		xt.remove(rv.tree);
		yt.remove(rv.tree);
		return getMaxSquareSize(rv.rect, xt, yt);
	}

	private static RemoveReturnValue removeATree(Rect rect, ArrayList<Position> xt, ArrayList<Position> yt) {
		// TODO Auto-generated method stub
		Position sideTree = null;
		Rect rtRect = null;
		int minDistance = Integer.MAX_VALUE;
		Position tree = xt.get(0);
		int distance = tree.x-rect.x;
		if (minDistance>distance) {
			minDistance = distance;
			sideTree = tree;
			rtRect = new Rect(tree.x+1, rect.y, rect.xLen-distance-1, rect.yLen);
		} 
		
		tree = xt.get(xt.size()-1);
		distance = rect.x+rect.xLen-1-tree.x;
		if (minDistance>distance) {
			minDistance = distance;
			sideTree = tree;
			rtRect = new Rect(rect.x, rect.y, rect.xLen-distance-1, rect.yLen);
		} 
		
		tree = yt.get(0);
		distance = tree.y-rect.y;
		if (minDistance>distance) {
			minDistance = distance;
			sideTree = tree;
			rtRect = new Rect(rect.x, tree.y+1, rect.xLen, rect.yLen-distance-1);
		} else if (minDistance == distance && rect.yLen>rect.xLen) {
			sideTree = tree;
			rtRect = new Rect(rect.x, tree.y+1, rect.xLen, rect.yLen-distance-1);
		}
		
		tree = yt.get(yt.size()-1);
		distance = rect.y+rect.yLen-1-tree.y;
		if (minDistance>distance) {
			minDistance = distance;
			sideTree = tree;
			rtRect = new Rect(rect.x, rect.y, rect.xLen, rect.yLen-distance-1);
		} else if (minDistance == distance && rect.yLen>rect.xLen) {
			sideTree = tree;
			rtRect = new Rect(rect.x, rect.y, rect.xLen, rect.yLen-distance-1);
		}
		
		RemoveReturnValue rt = new RemoveReturnValue();
		rt.rect = rtRect;
		rt.tree = sideTree;
		
		return rt;
	}
}

class RemoveReturnValue {
	Position tree;
	Rect rect;
}

class Position {
	int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Rect {
	int x;
	int y;
	int xLen;
	int yLen;
	
	public Rect(int x, int y, int xLen, int yLen) {
		this.x = x;
		this.y = y;
		this.xLen = xLen;
		this.yLen = yLen;
	}

	public int squareSize() {
		// TODO Auto-generated method stub
		return this.xLen<this.yLen?this.xLen:this.yLen;
	}
}