import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Query {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream(new File("index.conf"));
			int ch=0;
			StringBuilder indexFilePath= new StringBuilder();
			int indicator = 0;
			Map<String,String> indexEntries = new HashMap<String,String>();
			Map<String,String> results = new HashMap<String,String>();
			
			StringBuilder sb= new StringBuilder();
			while ((ch=fis.read())!=-1)
			{
				if((char)ch==':'&&indicator==0)
				{
					indicator=1;
					continue;
				}
				if(indicator==1)
					indexFilePath.append((char)ch);
			}
			fis.close();
			if (args.length == 0)
				throw new UserException(
						"Please provide input fields to query");
			fis=new FileInputStream(new File(indexFilePath.toString()));
			String[] keyValPair = null;
			while((ch=fis.read())!=-1)
			{
				if(ch!=(int)'\n')
					sb.append((char)ch);
				if(ch==(int)'\n')
				{
					keyValPair=sb.toString().split(":");
					indexEntries.put(keyValPair[0],keyValPair[1]);
					sb=new StringBuilder();
				}
			}
			fis.close();
			String inputEntry ="";
			for (int i = 0; i < args.length; i++) {
				sb=new StringBuilder();
				indicator=0;
				for(int j=0;j<args[i].length();j++)
				{
					if(args[i].charAt(j)==':'&&indicator==0)
					{
						indicator=1;
						if(j!=1)
							throw new UserException("Invalid Input: Enter plain text or Example \"t:sachin\" ");
						continue;
					}
					if(indicator==1)
					{
						sb.append(args[i].charAt(j));
					}
				}
				
				if(indicator==0)
					inputEntry=args[i].trim();
				else
					inputEntry=sb.toString().trim();
				
				if(indexEntries.containsKey(inputEntry.toLowerCase()))
					results.put(args[i],indexEntries.get(inputEntry));
				else
					results.put(args[i]," ");
			}
			Set<String> queryWords=results.keySet();
			for(String qWord:queryWords)
				System.out.println(results.get(qWord));
		} catch (IOException | UserException e) {
			System.out.println(e.getMessage());
		}
	}
}
