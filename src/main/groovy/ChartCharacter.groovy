import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UProperty

class ChartCharacter {
	private String str
	private Integer code
	private languages = [:]
	private category

	public ChartCharacter(String str, String category, String lang, String langConn) {
		this.str = str
		this.category = category
		this.code = UCharacter.codePointAt(str, 0)
		this.languages[lang] = langConn
	}

	public addLanguage(String lang, String langConn) {
		this.languages[lang] = langConn
	}

	public String getLanguage(String lang) {
		if (this.languages.containsKey(lang)) {
			return this.languages[lang]
		}
		return "✕"
	}

	public String getCategory() {
		return this.category
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
		if (!(UCharacter.getName(this.code)) && (UCharacter.isISOControl(this.code))) {
			return "<control>"
		}
		return UCharacter.getName(this.code)
	}

	public Boolean isASCII (){
		return this.code < 128;
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
