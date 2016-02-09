import groovy.json.JsonSlurper

class CLDRLocale {
	String[] exemplar
	String[] auxiliary
	String[] punctuation

	public CLDRLocale(String locale) {
		JsonSlurper slurper = new JsonSlurper()
		File f = new File("cldr/"+locale+".json")
		def result = slurper.parse(f)
		this.exemplar = expandCharactersList(result.characters.exemplarCharacters)
		this.auxiliary = expandCharactersList(result.characters.auxiliary)
		this.punctuation = expandCharactersList(result.characters.punctuation)
	}

	public String[] getExemplarCharacters() {
		return exemplar
	}

	public String[] getAuxiliaryCharacters() {
		return auxiliary
	}

	public String[] getPunctuations() {
		return punctuation
	}

	private String[] expandCharactersList(String s) {
		if (s.startsWith("[")) {
			s = s.substring(1, s.length())
		}
		if (s.endsWith("]")) {
			s = s.substring(0, s.length() - 1)
		}
		s = s.replaceAll(" ", "")
	}
}
