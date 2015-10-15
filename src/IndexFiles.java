import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class IndexFiles {
	public static void main(String[] args) {
		String fName = ""; 
		String indexFolder="";
		File file = null;
		File conf = null;
		File indexFile = null;
		Map<String,List<String>> indextokens=null;
		try {
			if (args.length == 2) {
				fName = args[0];
				indexFolder = args[1];
				if(!fName.substring(fName.length()-3, fName.length()).equals("xml"))
					throw new HandleUserException("Invalid File format. Should be an XML file");
				file = new File(fName);
				if (!file.exists()) {
					throw new HandleUserException("No Such file exists");
				}
				String fName2 = fName.substring(0, fName.length() - 4)	+ "1";
				Parser parser;
				parser = new Parser();
				StemWord stemWrd;
				stemWrd = new StemWord();
				parser.HandleSpclCHar(fName, fName2);
				String[] documentWords = null;
				List<ParsedData> list = parser.parseData(new File(fName2));
				Map<String, String> dataMap = null;
				int pageCount = 0;
				File dir = new File(indexFolder);
				if(!dir.isDirectory()||!dir.exists())
				{
					dir.mkdirs();
				}
				indexFile = new File(indexFolder+"/IndexFile");
				if (indexFile.exists())
					indexFile.delete();
				conf = new File("index.conf");
				if(conf.exists())
					conf.delete();
				conf.createNewFile();
				FileOutputStream fos = new FileOutputStream(conf);
				String IndexabsoluteFilePath= "IndexPath:"+indexFile.getAbsolutePath();
				char[] charPath =  IndexabsoluteFilePath.toCharArray();
				for(int i=0;i<charPath.length;i++)
					fos.write(charPath[i]);
				fos.close(); 
				for (ParsedData pData : list) {
					pData.setDocText(TokenizeDoc.removeUrl(pData.getDocText()));
					dataMap = new HashMap<String, String>();
					documentWords = TokenizeDoc.splitWords(pData.getDocText(), "DocuText");
					List<String> docWordList = HandleStopWords.removeStopWords(documentWords,pData.getId());
					for (String temp:docWordList) {
						if (temp.length() >= 3) {
							temp = stemWrd.stem(temp);
							if (!dataMap.containsKey(temp)) {
								dataMap.put(temp,	pData.getId());
							}
						}
					}
					indextokens=ReadWriteData.tokenizeDump(dataMap, pageCount++,indextokens);
				}
			}
			
			else
				if(args.length!=2)
					throw new HandleUserException("Invalid Number of arguments");
			ReadWriteData.writeData(indextokens,indexFile);
		} catch (HandleUserException | ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
