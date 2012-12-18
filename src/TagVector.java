import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @ClassName: TagVector
 * @Description: transform raw html code into tag vector like:
 * "body:1;tr:1;link:4;div:16;img:5;td:1;br:9;meta:1;title:1;area:1;style:1;map:1;ol:1;head:1;h3:1;h2:4;ul:5;form:2;a:40;label:3;table:1;script:13;li:33;input:7;tbody:1;html:1;span:6;"
 * and calculate the distances between two tag vectors based on Euclidean distance
 * @author zhouyu
 * @date 2012-12-18 下午4:43:09
 * @version V0.1 
 *
 */
public class TagVector 
{
	private static HashMap<String, Integer> vector = new HashMap<String, Integer>();
	private static String[] ignoreTag = {"p","br","strong","font"};		//ignore these tags
	private static HashSet<String> ignoreTagSet = new HashSet<String>();
	static
	{
		for(int i = 0;i < ignoreTag.length;i++)
			ignoreTagSet.add(ignoreTag[i]);
	}
	
	/**
	 * 
	 * @Title: toTagVector
	 * @Description: convert raw html string into tag vector
	 * @param html denotes for the raw html document of a web page
	 * @return tag vector of the web page
	 * @throws
	 */
	public static String toTagVector(String html)
	{
		vector = null;
		vector = new HashMap<String, Integer>();
		Document doc = Jsoup.parse(html);
		Element root = doc.child(0);
		Elements tags = root.getAllElements();
		for(Element e:tags)
		{
			if(!ToLblTree.getLegalTag().contains(e.nodeName()) 
					|| ignoreTagSet.contains(e.nodeName())
					|| e.attr("style").contains("display:none"))
				continue;
			
			if(vector.get(e.nodeName()) == null)
				vector.put(e.nodeName(), 1);
			else
				vector.put(e.nodeName(), vector.get(e.nodeName()) + 1);
		}
		StringBuilder result = new StringBuilder("");
		
		for(String key:vector.keySet())
			result.append(key + ":" + vector.get(key) + ";");
		return result.toString();
	}
	
	/**
	 * @Title: calDis
	 * @Description: calculate the distance between two tag vectors
	 * @param va denotes for the first tag vector
	 * @param vb denotes for the second tag vector
	 * @return the Euclidean distance between va and vb
	 * @throws
	 */
	public static double calDis(String va,String vb)
	{
		HashMap<String, Integer> vectora = new HashMap<String, Integer>();
		HashMap<String, Integer> vectorb = new HashMap<String, Integer>();
		String[] slicea = va.split(";");
		String[] sliceb = vb.split(";");
		for(int i = 0;i < slicea.length;i++)
		{
			String[] tmpa = slicea[i].split(":");
			vectora.put(tmpa[0], Integer.parseInt(tmpa[1]));
		}
		
		for(int i = 0;i < sliceb.length;i++)
		{
			String[] tmpb = sliceb[i].split(":");
			vectorb.put(tmpb[0], Integer.parseInt(tmpb[1]));
		}
		
		HashSet<String> keys = new HashSet<String>();
		for(String key:vectora.keySet())
			keys.add(key);
		for(String key:vectorb.keySet())
			keys.add(key);
		
		double distance = 0.0;
		for(String key:keys)
		{
			int ca = 0;
			int cb = 0;
			if(vectora.get(key) != null)
				ca = vectora.get(key);
			if(vectorb.get(key) != null)
				cb = vectorb.get(key);
			distance += Math.pow((ca * 1.0 - cb * 1.0),2);
		}
		distance = Math.sqrt(distance);
		return distance;
	}
	
	public static void main(String[] args)
	{
		String a = "body:1;tr:1;link:4;div:16;img:5;td:1;br:9;meta:1;title:1;area:1;style:1;map:1;ol:1;head:1;h3:1;h2:4;ul:5;form:2;a:40;label:3;table:1;script:13;li:33;input:7;tbody:1;html:1;span:6;";
		String b = "body:1;tr:1;link:4;div:16;img:5;td:1;br:86;meta:1;title:1;area:1;style:1;map:1;ol:1;head:1;h3:1;h2:4;ul:5;form:2;a:41;label:3;table:1;script:13;li:34;input:7;tbody:1;strong:7;p:1;html:1;span:6;";
		double dis = calDis(a,b);
		System.out.println(dis);
	}
}
