
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: Sample
 * @Description: sample web pages from a website taking URL 
 * as argument and return a list of web pageid.
 * usage:
 * 		Sample sample = new Sample();
 *		List<Integer> samples = sample.getSample();
 * @author zhouyu
 * @date 2012-12-12 下午7:40:22
 * @version V0.1 
 *
 */
public class Sample {
	
	private int pageNum;
	public static final int matrixOffset = 5000;
	public int pageOffset;
	private final int perCluster = 1;
	private List<Integer> samplePageids;
	
	public Sample(){
		try {
			samplePageids = new ArrayList<Integer>();
			int count = 1;
			while(matrixOffset < pageNum / count)
				count++;
			pageOffset = pageNum / count + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: getSample
	 * @Description: sampling html document according its tag structure
	 * @param htmls denotes for a list contain the html documents from which sampling is done
	 * @return List<Integer> denotes for a list of sampling pageid
	 * @throws
	 */
	public List<Integer> getSample(List<String> htmls){
		int base = 0;
		while(base < pageNum){

			List<String> tagVectors = new ArrayList<>();
			for(String html:htmls)
				tagVectors.add(TagVector.toTagVector(html));
			
			/*
			 * the distance metric can also be switched to tree edit distance
			 * List<String> labelTrees = new ArrayList<>();
			 * for(String html:htmls)
			 * 		labelTrees.add(ToLblTree.convert(html));
			 */
			
			int tmpPageNum = tagVectors.size();
			double[][] disMatrix = new double[tmpPageNum][tmpPageNum];
			double maxDis = Double.MIN_VALUE;
			for(int i = 0;i < tmpPageNum;i++)
				for(int j = i + 1;j < tmpPageNum;j++)
				{
					double distance = TagVector.calDis(tagVectors.get(i), tagVectors.get(j));
					//double distance = new RTEDCommandLine().calTreeEditDistance(labelTrees.get(i), labelTrees.get(j));
					disMatrix[i][j] = distance;
					disMatrix[j][i] = distance;
					maxDis = maxDis < distance ? distance : maxDis;
				}
			
			HierarchicalCluster hiCluster = new HierarchicalCluster(tmpPageNum);
			hiCluster.cluster(disMatrix, maxDis * 0.05);
			for(int key : hiCluster.Sample(perCluster))
				samplePageids.add(key);

			base += pageOffset;
		}
		return samplePageids;
	}

	public static void main(String args[]){
		Sample sample = new Sample();
		List<String> htmlLists = new ArrayList<>();
		
		/*
		 * you jobs to add raw html pages into htmlLists
		 */
		
		for(int pageid:sample.getSample(htmlLists))
			System.out.println(pageid);
	}
}