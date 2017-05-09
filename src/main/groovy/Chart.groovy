import au.com.bytecode.opencsv.CSVWriter;

class Chart {
	private List characters = []

	public addCharacter(Character ch, String category, String lang, String langConn) {
		addCharacter((String) ch, category, lang, langConn)
	}

	public addCharacter(String str, String category, String lang, String langConn) {
		ChartCharacter ch = new ChartCharacter(str, category, lang, langConn)
		// if (ch.isASCII()) { // ignore ASCII
		// 	return
		// }
		ChartCharacter existing = this.characters.find { it.getCode() == ch.getCode() }
		if (existing == null) {
			this.characters.add(ch)
			return
		}
		if (existing.getCategory() != category) {
			println "error!"
		}
		if (existing.getLanguage(lang) == "✕") {
			existing.addLanguage(lang, langConn)
		}
	}

	private void exportCSV(String fname) {
		characters = characters.sort {
			it.getCode()
		}
        FileWriter fw = new FileWriter(fname)
        CSVWriter writer = new CSVWriter(fw);

        String[] header = ["Character", "UCS", "Name", "Ar", "Fa", "Category"]
        writer.writeNext(header)

		this.characters.each {
			String[] values = [it.getString(), it.getCodeString(), it.getName(),
							   it.getLanguage("ar"), it.getLanguage("fa"),
							   it.getCategory()]
			writer.writeNext(values)
		}

        writer.close()
	}
}
