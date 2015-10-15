import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ReadWriteData {
	public static Map<String, List<String>> tokenizeDump(
			Map<String, String> dataMap, int pageCount,
			Map<String, List<String>> indextokens) throws HandleUserException {
		List<String> temp = null;
		Set<String> keys = dataMap.keySet();

		for (String key : keys) {
			if (pageCount == 0) {
				indextokens = new TreeMap<String, List<String>>();
				temp = new ArrayList<String>();
				temp.add(dataMap.get(key));
			}
			if (pageCount > 0) {
				if (indextokens.containsKey(key)) {
					temp = indextokens.get(key);
					temp.add(dataMap.get(key));
				} else {
					temp = new ArrayList<String>();
					temp.add(dataMap.get(key));
				}

			}
			indextokens.put(key, temp);

		}
		return indextokens;

	}

	public static void writeData(Map<String, List<String>> indextokens,
			File postingIndexFile) throws IOException {
		if (!postingIndexFile.exists())
			postingIndexFile.createNewFile();
		else
		{	postingIndexFile.delete();
			postingIndexFile.createNewFile();
		}
		FileOutputStream fos2 = new FileOutputStream(postingIndexFile);
		Set<String> words = indextokens.keySet();
		StringBuilder entry = null;
		List<String> docList = null;
		for (String word : words) {
			if(word.length()>0)
			{				
				entry = new StringBuilder();
				docList = indextokens.get(word);
				Collections.sort(docList);
				entry.append(word + ":");
				for (String doc : docList) {
					entry.append(doc + ",");
				}
				for (int i = 0; i < entry.length() - 1; i++) {
					fos2.write(entry.charAt(i));
				}
				fos2.write('\n');
			}
		}
		fos2.close();
	}
}
