

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @ClassName: HierarchicalCluster
 * @Description: hierarchical clustering with single-linkage method
 * @author zhouyu
 * @date 2012-12-12 6:21:53
 * @version V0.1 
 *
 */
public class HierarchicalCluster {
	private HashMap<Integer, List<Integer> > clusters;
	DisjointSets ds;
	private int countNum;
	private int N;
	
	public HierarchicalCluster(int elementNum) {
		N = elementNum;
		ds = new DisjointSets(N);
		countNum = N;
		clusters = new HashMap<Integer, List<Integer> >();
	}

	/**
	 * 
	 * @Title: Sample
	 * @Description: random sample n element within one cluster
	 * @param element per cluster when sampling
	 * @return List<Integer>
	 * @throws
	 */
	public List<Integer> Sample(int n) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < N; i++) {
			clusters.put(ds.root(i), new ArrayList<Integer>());
		}
		for (int i = 0; i < N; i++) {
			clusters.get(ds.root(i)).add(i);
		}
		for(int key : clusters.keySet()){
			List<Integer> theCluster = clusters.get(key);
			Collections.shuffle(theCluster);	//random shuffle
			for(int i = 0;i < theCluster.size() && i < n;i++){
				result.add(theCluster.get(i));
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: cluster
	 * @Description: this method provides a hierarchical way for clustering data
	 * @param distance denote the distance matrix
	 * @param dis denote the threshold to stop clustering
	 * @return void
	 * @throws
	 */
	public void cluster(double[][]distance, double dis){
		while(true){
			int mx = 0, my = 0;
			double vmin = Double.MAX_VALUE;
			
			/*
			 * find the minimum distance element
			 */
			for (int i = 0; i < N; i++) { 	
				for (int j = i + 1; j < N; j++) {
					if (vmin > distance[i][j]) {
						vmin = distance[i][j];
						mx = i;
						my = j;
					}
				}
			}
			
			/*
			 * if the minimum distance is bigger than a threshold dis, 
			 * then clustering finish
			 */
			if (vmin > dis) {
				break;
			}
			
			ds.union(ds.root(mx), ds.root(my)); //union the two set

			double hx[] = distance[mx];
			double hy[] = distance[my];
			double vx[] = new double[N];
			double vy[] = new double[N];
			/*
			 * update the new distance matrix
			 */
			for (int i = 0; i < N; i++) {
				double tm = Math.min(hx[i], hy[i]);
				if (tm != 0)
					vx[i] = tm;
				else
					vx[i] = Double.MAX_VALUE;
				vy[i] = Double.MAX_VALUE;
			}
			
			/*
			 * update the new row and column distance of the distance matrix
			 */
			distance[mx] = vx;
			distance[my] = vy;
			for (int i = 0; i < N; i++) {
				distance[i][mx] = vx[i];
				distance[i][my] = vy[i];
			}
		}
	}

	/**
	 * 
	 * @Title: cluster
	 * @Description: this method provides a hierarchical way for clustering data
	 * @param distance denote the distance matrix
	 * @param cnum denote the number of final clusters
	 * @return void
	 * @throws
	 */
	public void cluster(double[][] distance, int cnum) {
		
		while (countNum > cnum) {// go on clustering until the cluster number is less than cnum
			int mx = 0, my = 0;
			double vmin = Double.MAX_VALUE;
			
			/*
			 * find the minimum distance element
			 */
			for (int i = 0; i < N; i++) {
				for (int j = i + 1; j < N; j++) {
					if (vmin > distance[i][j]) {
						vmin = distance[i][j];
						mx = i;
						my = j;
					}
				}
			}
			
			ds.union(ds.root(mx), ds.root(my));	//union the two set
			
			double hx[] = distance[mx];
			double hy[] = distance[my];
			double vx[] = new double[N];
			double vy[] = new double[N];
			/*
			 * update the new distance matrix
			 */
			for (int i = 0; i < N; i++) {
				double tm = Math.min(hx[i], hy[i]);
				if (tm != 0)
					vx[i] = tm;
				else
					vx[i] = Double.MAX_VALUE;
				vy[i] = Double.MAX_VALUE;
			}
			distance[mx] = vx;
			distance[my] = vy;
			for (int i = 0; i < N; i++) {
				distance[i][mx] = vx[i];
				distance[i][my] = vy[i];
			}
			countNum--;	//update the number of the rest of the clusters
		}
	}
	
	public static void main(String args[]) {
		double[][] r = { { 0, 1, 4, 6, 8, 9 }, { 1, 0, 3, 5, 7, 8 },
				{ 4, 3, 0, 2, 4, 5 }, { 6, 5, 2, 0, 2, 3 },
				{ 8, 7, 4, 2, 0, 1 }, { 9, 8, 5, 3, 1, 0 } };
		HierarchicalCluster hicl = new HierarchicalCluster(6);
		//hicl.cluster(r, 1.0);
		hicl.cluster(r,3);
		for(int e:hicl.Sample(1))
			System.out.println(e);
	}
}