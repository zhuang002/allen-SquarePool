import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static int n;
	static int t;
	
	static ArrayList<Interval> intervals = new ArrayList<>();
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
			ytrees.add(tree);
		}
		
		ytrees.add(new Position(-1,-1));
		ytrees.add(new Position(n, n));
		
		Collections.sort(ytrees, (a,b)->a.y-b.y);
		
		for (int i=0;i<ytrees.size();i++) {
			Position tree1 = ytrees.get(i);
			for (int j=i+1;j<ytrees.size();j++) {
				Position tree2 = ytrees.get(j);
				if (tree1.x != tree2.x) {
					Interval interval = new Interval(tree1,tree2);
					if (interval.distance>0)
						intervals.add(interval);
				}
			}
		}
		Collections.sort(intervals, (a, b)->b.distance - a.distance);

		
		
		int maxSquareSize = 0;
		for (Interval interval:intervals) {
			int squareSize = getSquareSizeFromXInterval(interval);
			if (maxSquareSize<squareSize)
				maxSquareSize = squareSize;
		}
		
		
		System.out.println(maxSquareSize);
	}
	
	private static int getSquareSizeFromXInterval(Interval interval) {
		// TODO Auto-generated method stub
		Position previousTree=null;
		int maxDistance = 0;
		int distance = 0;
		for (Position tree:ytrees) {
			if (interval.containsPos(tree)) {
				
				if (previousTree != null) {
					distance = tree.y - previousTree.y-1;
				} else { // handle the left most spance
					distance = tree.y-interval.tree1.y-1;
				}
				if (maxDistance < distance)
					maxDistance = distance;
				previousTree = tree;
			}
			
		}
		// handle the right most space
		if (previousTree == null) {
			distance = ytrees.size() - ytrees.get(0).y;
		} else {
			distance = ytrees.size() -  previousTree.y -1;
		}
		if (maxDistance < distance)
			maxDistance = distance;
		
		if (interval.distance < maxDistance) {
			return interval.distance;
		}
		return maxDistance;
		
	}

	
}

class Interval {
	Position tree1;
	Position tree2;
	int distance;
	
	public Interval(Position tree1, Position tree2) {
		if (tree1.x < tree2.x) {
			this.tree1 = tree1;
			this.tree2 = tree2;
			this.distance = tree2.x-tree1.x-1;
		} else {
			this.tree1 = tree2;
			this.tree2 = tree1;
			this.distance = tree1.x - tree2.x-1;
		}
	}

	public boolean containsPos(Position pos) {
		// TODO Auto-generated method stub
		return this.tree1.x < pos.x && this.tree2.x > pos.x;
	}
}


class Position {
	int x, y;
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

