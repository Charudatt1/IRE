public class ParsedData {
	private String title;
	private String id;
	private String docText = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocText() {
		return docText;
	}

	public void setDocText(String docText) {
		this.docText = docText;
	}

	@Override
	public String toString() {
		return "ParsedData [title=" + title + ", docText=" + docText + ", id="
				+ id + "]";
	}



}
