sampling
========
<pre>
sampling web pages among pages within one website based on the html tag structure
dependency:jsoup-1.6.3.jar

usage:
	Sample sample = new Sample();
	List<String> htmlLists = new ArrayList<>();	
	/*
	* you job to add raw html pages into htmlLists
	*/
	for(int pageid:sample.getSample(htmlLists))
		System.out.println(pageid);

algorithm describe:
	1.Define distance matric: the Euclidean distance  or tree edit distance of the html documents
	2.Hierarchical clustering until the minimum distance among all of the two clusters is larger than a threhold a
	3.Random select b samples among each cluster clustering from step 2.
</pre>
