import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class AcceptTriplet {
	HashMap<String, List<String>> mapPredicates = new HashMap<>();

	AcceptTriplet() {

	}

	String[] stopwords = { "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
			"yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
			"itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
			"these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
			"do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
			"of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
			"after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
			"further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
			"few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
			"too", "very", "s", "t", "can", "will", "just", "don", "should", "now" };

	List<String> stopWordsList = Arrays.asList(stopwords);

	

	
	double searchInternet(String[] fact) throws ClassNotFoundException, IOException {
		FetchOnline fetchOnline = new FetchOnline();

		fact[2] = predicate;
		double confidence = fetchOnline.fetch(fact);

		return confidence;
	}

	int intersectionList(List<String> fact1, List<String> fact2) {
		fact1.retainAll(fact2);
		return fact1.size();
	}

	int unionList(List<String> fact1, List<String> fact2) {
		int length = 0;
		for (String factToken : fact2) {
			if (!fact1.contains(factToken))
				length++;
		}

		length += fact1.size();

		return length;
	}

	String predicate = "";

	String predicateFind(String text) {
		// {"award","spouse","place","stars","authors","subordinate","better
		// half","wife","team"};
		
		
		text=text.replaceAll("'s", "ZZZ");
		text=text.replaceAll("[^ 0-9a-zA-Z]", "");
		String[] textTokens = text.split(" ");
		String filteredText = "";
		if (text.contains("better half")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("better"))) {
					i = i + 1;
					continue;
				} else
					filteredText+=(textTokens[i] + " ");

			}
			predicate = "spouse";
		} else if (text.contains("wife")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("wife"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "spouse";
		} else if (text.contains("spouse")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("spouse"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "spouse";
		} else if (text.contains("place")) {
			int j = 0;
			List<String> listTemp=new ArrayList<>();
			for (int i = textTokens.length - 1; i >= 0; i--) {
				if (textTokens[i].equals("place")) {
					i--;
					j = i;
					continue;
				} else
					{
//				
					listTemp.add(textTokens[i]);
					
					}
			}
			
			for(int i=listTemp.size()-1;i>=0;i--){
				filteredText+=(listTemp.get(i) + " ");
			}
			
			if( textTokens[j].equals("death") || textTokens[j].equals("last"))
				predicate = "Died";
			else if(textTokens[j].equals("birth") || textTokens[j].equals("nascence") )
				predicate = "Born";
			else if(textTokens[j].equals("innovation"))
				predicate = "Innovation place";

		}else if (text.contains("subsidiary")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("subsidiary"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "Died";} 
		
		
		else if (text.contains("stars")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("stars"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "starring";}
			else if (text.contains("award")) {
				for (int i = 0; i < textTokens.length; i++) {
					if ((textTokens[i].equals("award"))) {
						continue;
					} else
						filteredText+=(textTokens[i] + " ");
				}
				predicate = "award";
			
		} else if (text.contains("team")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("team"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "team";
		} else if (text.contains("author")) {
			for (int i = 0; i < textTokens.length; i++) {
				if ((textTokens[i].equals("author"))) {
					continue;
				} else
					filteredText+=(textTokens[i] + " ");
			}
			predicate = "author";
		}
		
		 else if (text.contains("role")) {
				for (int i = 0; i < textTokens.length; i++) {
					if ((textTokens[i].equals("role"))) {
						continue;
					} else
						filteredText+=(textTokens[i] + " ");
				}
				predicate = "prime minister";
			}
		
		
		return filteredText.toLowerCase().trim();

	}


	void processing(String fileName, String outputFile) throws ClassNotFoundException, IOException {

		final String P1 = "<http://swc2017.aksw.org/task2/dataset/";
		final String P2 = "<http://swc2017.aksw.org/hasTruthValue>\"";
		final String P3 = "\"^^<http://www.w3.org/2001/XMLSchema#double>";
		File file=new File(outputFile);
		String[] facts = FileUtils.readFileToString(new File(fileName), "UTF-8").split("\n");
		String result="";
		for(int i=1;i<facts.length;i++){
		String[] factToken = facts[i].split("\t");
			String[] questionFact = tokenize(factToken[1]);
			double value=0;
			if(questionFact[0]==""||questionFact[0]==null)
				value=1;
			if(value==0)
				value = searchInternet(questionFact);
			result+=P1+factToken[0]+">"+P2+value+P3+".\n";
		}
		FileUtils.writeStringToFile(file, result, "UTF-8");
	}
	
	void processFact(String text) throws ClassNotFoundException, IOException{
		String[] questionFact = tokenize(text);
		double value = searchInternet(questionFact);
		System.out.println(value);
	}
	
	
	String[] tokenize(String sentence) {
		// List<String> listPred = mapPredicates.get(predicate);
		String filteredSentence = predicateFind(sentence);
		String[] tokens = null;
		String[] tuple = new String[3];
		if (filteredSentence.contains(" is ")) {
			tokens = filteredSentence.split(" is ");
			for (int i = 0; i < tokens.length; i++) {
				tuple[i] = removeStop(tokens[i]);
			}
		}
		else{
			String tokens1[] = filteredSentence.split(" ");
			if(tokens1.length==4){
				tuple[0]=tokens1[0]+" "+tokens1[1];
				tuple[1]=tokens1[2]+" "+tokens1[3];
			}
		}
		return tuple;
	}

	
	String removeStop(String sentence) {
		String[] sentTokens = null;
		sentence = sentence.replaceAll("[^ a-zA-Z0-9]", "");
		if (sentence.contains(" "))
			sentTokens = sentence.split(" ");
		else
			return sentence;
		String result = "";
		for (String token : sentTokens) {
			
				result += token + " ";
			
		}

		return result.trim();
	}
	
	
}
