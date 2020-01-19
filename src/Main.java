import java.io.IOException;

public class Main {
	public static void main(String args[]) throws ClassNotFoundException, IOException {
		AcceptTriplet obj = new AcceptTriplet();
		String inputFile = "SNLP2019_training.tsv";
		String outputFile = "output.ttl";
		obj.processing(inputFile, outputFile);
//		obj.processing("/Users/akshitbhatia/Downloads/SNLP2019_training.tsv");
//		obj.processFact("Ted Harbert is Chelsea Handler's better half");
		// obj.processing("Ted Harbert is Akshit Bhatia's better half");
		// New York City is IBM's innovation place.
		// obj.processing("New York City is IBM's innovation place.");
	}
}
