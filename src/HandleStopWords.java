import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandleStopWords {

	static File f = null;

	public static List<String> readStopWords(String stopWordsFilename)
			throws UserException {
		List<String> stopWordsList = new ArrayList<String>();
		f = new File(stopWordsFilename);
		if (!f.exists()) {
			throw new UserException("No Stopword file found");
		}
		Scanner stopWordsFile;
		try {
			stopWordsFile = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new UserException("No Stopword file found");
		}
		while (stopWordsFile.hasNext())
			stopWordsList.add(stopWordsFile.next());
		stopWordsFile.close();
		return stopWordsList;
	}

	public static List< String> removeStopWords(
			String[] docWords, String id)
			throws UserException
	{
		List<String> dwList = new ArrayList<String>();
		List<String> swList = readStopWords("../src/stopwords.dat");
		for (String temp : docWords) {
			if (!swList.contains(temp)) {
				dwList.add(temp);
			}
		}
		return dwList;
	}
}
