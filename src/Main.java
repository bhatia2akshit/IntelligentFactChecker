import java.io.IOException;

public class Main {
	public static void main(String args[]) throws ClassNotFoundException, IOException {
		AcceptTriplet obj = new AcceptTriplet();
		String inputFile = "SNLP2019_training.tsv";
		String outputFile = "output.ttl";
		obj.processing(inputFile, outputFile);
	}
}
