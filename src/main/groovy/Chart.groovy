import au.com.bytecode.opencsv.CSVWriter;

class Chart {
	private List characters = []

	public addCharacter(Character ch, String lang, String langCon) {
		addCharacter((String) ch, lang, langCon)
	}

	public addCharacter(String str, String lang, String langCon) {
		ChartCharacter ch = new ChartCharacter(str, lang, langCon)
		// if (ch.isASCII()) { // ignore ASCII
		// 	return
		// }
		ChartCharacter existing = this.characters.find { it.getCode() == ch.getCode() }
		if (existing == null) {
			this.characters.add(ch)
			return
		}
		if (existing.getLanguage(lang) == "✕") {
			existing.addLanguage(lang, langCon)
		}
	}

	private void exportCSV(String fname) {
		characters = characters.sort {
			it.getCode()
		}
        FileWriter fw = new FileWriter(fname)
        CSVWriter writer = new CSVWriter(fw);

        String[] header = ["Character", "UCS", "Name", "Ar", "Fa"]
        writer.writeNext(header)

		this.characters.each {
			String[] values = [it.getString(), it.getCodeString(), it.getName(),
							   it.getLanguage("ar"), it.getLanguage("fa")]
			writer.writeNext(values)
		}

        writer.close()
	}
}
