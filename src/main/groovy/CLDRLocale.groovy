import groovy.json.JsonSlurper

class CLDRLocale {
	String[] exemplar = []
	String[] auxiliary = []
	String[] punctuation = []

	public CLDRLocale(String locale) {
		JsonSlurper slurper = new JsonSlurper()
		String[] fileNames = ["cldr/"+locale+".json"]
		fileNames.each {
			File f = new File(it)
			def result = slurper.parse(f)
			this.exemplar.plus(expandCharactersList(result.characters.exemplarCharacters))
			this.auxiliary.plus(expandCharactersList(result.characters.auxiliary))
			this.punctuation.plus(expandCharactersList(result.characters.punctuation))
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
