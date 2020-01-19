import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchOnline {
	private static final String URL = "https://en.wikipedia.org/w/index.php?search=";
	private static final String[] X = { ".mw-parser-output > p", ".mw-parser-output > ul li", ".infobox.vcard tr",
			".mw-parser-output .multicol > tbody li" };
	private static final String Xa = "infobox";

	public void searchInWiki(){
		
	}
	
	
	public double fetch(String[] facts) throws UnsupportedEncodingException, IOException {
		
		String queryToSearch="";
		String queryToMatch="";
		if(facts[0].contains("zzz")){
			queryToSearch=facts[0].replaceAll("zzz", "");
			queryToMatch=facts[1];
		}else if(facts[1].contains("zzz")){
			queryToSearch=facts[1].replaceAll("zzz", "");
			queryToMatch=facts[0];
		}else{
			queryToSearch=facts[0];
			queryToMatch=facts[1];
		}
		
		
		Document doc = Jsoup.connect(URL + URLEncoder.encode(queryToSearch, "UTF8")).get();
		Element set = doc.getElementsByClass("infobox").first();
		if(set==null)
			return 0;
		Elements a = set.select("tr");
		double confidence = 0;
		int min =10000;
		int limit = a.size();
		int in=0;
		for (Element ab : a) {
			in++;
			if (ab.getElementsContainingOwnText(facts[2]).hasText()) {
				String s = ab.text();
				s=s.replaceAll("[^ a-z0-9A-Z]", "");
				String[]  stemp = s.split(" ");
				String matchRegex = "";
				String minMatchReg ="";
				String[] tokenRegex = queryToMatch.split(" ");
				int lengthRegex = tokenRegex.length;
				for(int i=0;i<stemp.length-1;i++){
					int j=i;
					matchRegex="";
					while(j-i<lengthRegex && j<stemp.length){
						matchRegex += stemp[j++]+" ";
					}
					
					
					int value = calculate(queryToMatch.toLowerCase(), matchRegex.toLowerCase());
					
					if(value<min)
					{	
//						System.out.println(matchRegex+" "+value);
						min=value;
						minMatchReg=matchRegex;
					}
					
					
				}
				int length=0;
				if(queryToMatch.length()>minMatchReg.length())
					length=queryToMatch.length();
				else length = minMatchReg.length();
				
				confidence=100-(double)min/length*100;
//				System.out.println(confidence);
				in=0;
				break;
			}
			
		}
		
		if(confidence<50){
			//confidence=(double)(confidence-50)/50;
			confidence=0.0;
		}
		else if(confidence>=50){
//			confidence=(double)(100-confidence)/50;
			confidence=1.0;
		}
//		else confidence=0;
		
		return confidence;
	}
	
	
	 public static int calculate(String string1, String string2) {
	      int distance = 0;
	        int s1 = string1.length();
//	        string2=string2.replaceAll("[^ 0-9a-zA-Z]","");
	        int s2 = string2.length();

	        int[][] table = new int[s1+1][s2+1];

	        for (int i = 0; i <= s1; i++) {

	            for (int j = 0; j <= s2; j++) {

	                if (i == 0) {

	                    table[i][j] = j;
	                } else if (j == 0) {
	                    table[i][j] = i;
	                } else if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
	                    table[i][j] = table[i - 1][j - 1];

	                } else {
	                    int small = table[i][j - 1];
	                    if (small > table[i - 1][j]) {
	                        small = table[i - 1][j];
	                    }
	                    if (small > table[i - 1][j - 1]) {
	                        small = table[i - 1][j - 1];
	                    }

	                    table[i][j] = 1 + small;
	                }

	            }

	        }

	        distance = table[s1][s2];

	       
	        return distance;
	    }
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		FetchOnline f = new FetchOnline();
		String[] s= {"Camp Rock","Nick Jonas","starring"};
		System.out.println(f.fetch(s));
	}
}
