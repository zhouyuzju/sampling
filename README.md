sampling
========

sampling web pages among pages within one website based on the html tag structure
dependency:jsoup-1.6.3.jar

usage:
Sample sample = new Sample();
List<String> htmlLists = new ArrayList<>();
		
/*
 * you jobs to add raw html pages into htmlLists
 */
		
for(int pageid:sample.getSample(htmlLists))
	System.out.println(pageid);