

import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ToLblTree 
{
	private static StringBuilder content;
	private static String[] legaltagset = {"!DOCTYPE","a","abbr","acronym","address",
		"applet","area","b","base","basefont","bdo","big","blockquote","body","br",
		"button","caption","center","cite","code","col",
		"colgroup","dd","del","dfn","dir","div","dl","dt","em",
		"fieldset","font","form","frame","frameset","head","h1","h2","h3","h4","h5",
		"h6","hr","html","i","iframe","img","input","ins","kbd","label","legend","li",
		"link","map","menu","meta","noframes","noscript","object","ol","optgroup",
		"option","p","param","pre","q","s","samp","script","select","small","span",
		"strike","strong","style","sub","sup","table","tbody","td","textarea","tfoot",
		"th","thead","title","tr","tt","u","ul","var"};
	private static String[] ignoretagset = {"p","strong","br","table","tr","td","th","tbody","thead"};
	private static HashSet<String> filterTag = new HashSet<String>();
	private static HashSet<String> legalTag = new HashSet<String>();
	static
	{
		for(int i = 0;i < ignoretagset.length;i++)
			filterTag.add(ignoretagset[i]);
		for(int i = 0;i < legaltagset.length;i++)
			legalTag.add(legaltagset[i]);
	}
	
	public static HashSet<String> getLegalTag()
	{
		return legalTag;
	}
	
	public static String convert(String html) {
		content = new StringBuilder();
		Document doc = Jsoup.parse(html);
		dfs(doc.child(0));
		return content.toString();
	}
	
	private static void dfs(Element node) {// ��ȱ���?
		Element child;
		boolean flag = false;
		if (node.children().size() < 1) {// ��Ҷ�ӽ��?
			child = null;
		} else {// ��Ҷ�ӽ��?
			child = node.child(0);
		}
		if (node != null && legalTag.contains(node.nodeName())
				&& !node.attr("style").contains("display:none") 
				&& !filterTag.contains(node.nodeName().toLowerCase())){
			// ��style���԰�display:none�Ĺ���,tagΪfiltertag��Ҳ����,tag���Ϸ�Ҳ����
			content.append("{");
			content.append(node.nodeName());
			flag = true;
		}
		
		while (child != null) {
			dfs(child);
			child = child.nextElementSibling();
		}
		if(flag)
			content.append("}");
	}
	
	public static void main(String[] args)
	{
		ToLblTree.convert("<div style=\"display:none\"><a href=\"a\"></a></div>");
	}
}
