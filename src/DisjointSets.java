/**
 * 
 * @ClassName: DisjointSets
 * @Description: simple implement of data structure disjoint set
 * @author zhouyu
 * @date 2012-12-18 下午4:16:15
 * @version V0.1 
 *
 */

public class DisjointSets {
	private int[] id;

	public DisjointSets(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) 
			id[i] = i;
	}
	
	/**
	 * 
	 * @Title: root
	 * @Description: get the root of the query point
	 * @param i denotes for the id of the query point
	 * @return the root id of the query point i
	 * @throws
	 */
	public int root(int i) {
		while (i != id[i])
			i = id[i];
		return i;
	}
	
	/**
	 * 
	 * @Title: union
	 * @Description: union the two set
	 * @param p denotes for the id of the first set
	 * @param q denotes for the id of the second set
	 * @return void
	 * @throws
	 */
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		id[i] = j;
	}
	
	
	/**
	 * @Title: connected
	 * @Description: judge whether the two points are connected
	 * @param p denotes for the id of the first point
	 * @param q denotes for the id of the second point
	 * @return whether point p and point q is connected
	 * @throws
	 */
	public boolean connected(int p, int q)
	{
		return root(p) == root(q);
	}
}
