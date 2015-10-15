import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TokenizeDoc {

	public static String[] splitWords(String inpString, String type) {
		String regex="[^a-zA-Z0-9]+";
		String[] words = inpString.split(regex);
		return words;
	}
	public static String removeUrl(String commentstr)
    {
        String commentstr1=commentstr;
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr1);
	String temp="";
	for(int i=0;i<m.groupCount()&&m.matches();i++) {
	    temp=m.group(i);
	    if(temp!=null)
            	commentstr1=commentstr1.replaceAll(temp,"").trim();
        }
        return commentstr1;
    }
}
