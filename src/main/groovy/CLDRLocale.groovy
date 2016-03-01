import groovy.json.JsonSlurper

class CLDRLocale {
	private List exemplar = []
	private List auxiliary = []
	private List punctuation = []

	public CLDRLocale(String locale) {
		JsonSlurper slurper = new JsonSlurper()
		String[] fileNames = ["cldr/"+locale+".json"]
		fileNames.each {
			File f = new File(it)
			def result = slurper.parse(f)
			this.exemplar.addAll(expandCharactersList(result.characters.exemplarCharacters))
			this.auxiliary.addAll(expandCharactersList(result.characters.auxiliary))
			this.punctuation.addAll(expandCharactersList(result.characters.punctuation))
		}
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
