import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser {

	static List<ParsedData> docList = new ArrayList<ParsedData>();
	static ParsedData pData = null;

	public DefaultHandler getHandler() {
		DefaultHandler handler = new DefaultHandler() {

			boolean handleText = false;
			boolean isTitle = false;
			boolean inPage = false;
			boolean isId = false;
			boolean isRevision = false;

			public void startDocument() throws SAXException {

			}

			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {

				if (qName.equalsIgnoreCase("page")) {
					pData = new ParsedData();
					inPage = true;
				}
				if (qName.equalsIgnoreCase("title") && inPage) {
					isTitle = true;

				}
				if (qName.equalsIgnoreCase("id") && !isRevision) {
					isId = true;
				}
				if (qName.equalsIgnoreCase("revision")) {
					isRevision = true;

				}
				if (qName.equalsIgnoreCase("text") && isRevision) {
					handleText = true;
				}

			}

			public void endElement(String uri, String localName, String qName)
					throws SAXException {

				if (qName.equalsIgnoreCase("page")) {
					inPage = false;
				}
				if (qName.equalsIgnoreCase("revision")) {
					isRevision = false;

				}
			}

			public void characters(char ch[], int start, int length)
					throws SAXException {
				if (isTitle) {
					pData.setTitle(new String(ch, start, length));
					isTitle = false;
				}

				if (isId) {
					pData.setId(new String(ch, start, length));
					isId = false;
				}

				if (handleText) {

					handleText = false;
					pData.setDocText(new String(ch, start, length));
					docList.add(pData);

				}

			}

		};
		return handler;
	}

	public List<ParsedData> parseData(File file)
			throws ParserConfigurationException, SAXException, IOException {

		InputStream inputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(is, getHandler());
		return docList;

	}

	public String HandleSpclCHar(String fileName, String fileName2)
			throws IOException {
		InputStream is = new FileInputStream(fileName);
		FileWriter fw = new FileWriter(fileName2);
		int ch = 0;
		StringBuilder otherText = new StringBuilder();
		while ((ch = is.read()) != -1) {
			switch ((char) ch) {
			case '\n':
			case '&':
			case '\r':
			case '[':
			case ']':
				ch=(int)'/';
			}
			otherText.append((char) ch);
		}
		fw.write(otherText.toString());
		fw.close();
		is.close();
		return "";
	}
}
