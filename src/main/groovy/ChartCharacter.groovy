import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UProperty

class ChartCharacter {
	private String str
	private Integer code

	private languages = [:]

	public ChartCharacter(String str, String lang, String langCon) {
		this.str = str
		this.code = UCharacter.codePointAt(str, 0)
		this.languages[lang] = langCon
	}

	public addLanguage(String lang, String langCon) {
		this.languages[lang] = langCon
	}

	public String getLanguage(String lang) {
		if (this.languages.containsKey(lang)) {
			return this.languages[lang]
		}
		return ""
	}

	public Integer getCode() {
		return this.code
	}

    public String getCodeString (){
        return "U+" + String.format("%04x", this.code).toUpperCase()
    }

	public String getString() {
		return this.str
	}

	public String getName() {
		return UCharacter.getName(this.code)
	}

    public Boolean isDiacritic (){
        return UCharacter.hasBinaryProperty(this.code, UProperty.DIACRITIC)
    }

    public Boolean isLetter (){
        return UCharacter.isLetter(this.code)
    }

    public Boolean isBidiControl (){
        return UCharacter.hasBinaryProperty(this.code, UProperty.BIDI_CONTROL)
    }

    public Boolean isDigit (){
        return UCharacter.isLetterOrDigit(this.code) && !this.isLetter()
    }

}
