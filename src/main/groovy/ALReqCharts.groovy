import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.text.DecimalFormatSymbols
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import groovy.json.JsonSlurper
import au.com.bytecode.opencsv.CSVWriter;

class ALReqCharts {
	Chart theChart = new Chart()

	private languages = ['ar', 'fa']

	public void generateCharts(){
		this.languages.each {
			addICUCharacters(it)
			addCLDRCharacters(it)
			if (it == 'fa') {
				addISIRI6219Characters(it)
			}
		}
		exportCSV()
	}

	private void exportCSV() {
		theChart.exportCSV("characters.csv")
	}

    private void addICUCharacters(String language){
		ULocale u = new ULocale(language)
        LocaleData ud = new LocaleData().getInstance(u)

		// add alphabets and diacritics
        UnicodeSet usEsStd = ud.getExemplarSet(0, ud.ES_STANDARD)
        UnicodeSet usEsAux = ud.getExemplarSet(0, ud.ES_AUXILIARY)
		usEsStd.iterator().each { addUnknownCharacter(it, language, "✓") }
		usEsAux.iterator().each { addUnknownCharacter(it, language, "✲") }

		// add punctuations
        UnicodeSet usEsPun = ud.getExemplarSet(0, ud.ES_PUNCTUATION)
		usEsPun.iterator().each { theChart.addCharacter(it, "punctuations", language, "✓") }

		// add numbers
		DecimalFormatSymbols numSyms = new DecimalFormatSymbols(u)
		numSyms.getDigits().each { theChart.addCharacter(it, "numbers", language,  "✓") }

		addNumberSymbols(language)
    }

	private void addNumberSymbols(String language) {
		ULocale u = new ULocale(language)
		DecimalFormatSymbols numSyms = new DecimalFormatSymbols(u)
		theChart.addCharacter(numSyms.getDecimalSeparator(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getGroupingSeparator(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getPercent(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getMinusSign(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getPlusSign(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getExponentMultiplicationSign(), "punctuations", language,  "✓")
		theChart.addCharacter(numSyms.getInfinity(), "punctuations", language,  "✓")
	}

	private void addCLDRCharacters(String language) {
		CLDRLocale l = new CLDRLocale(language)
		l.getExemplarCharacters().each() { addUnknownCharacter(it, language, "✓") }
		l.getAuxiliaryCharacters().each() { addUnknownCharacter(it, language, "✲") }
		l.getPunctuations().each() { theChart.addCharacter(it, "punctuations", language, "✓") }
	}

	private void addISIRI6219Characters(String language) {
		JsonSlurper slurper = new JsonSlurper()
		File f = new File("isiri-6219/isiri-6219.json")
		def result = slurper.parse(f)
		result.each {
			String codestr = it.code
			if (codestr.startsWith("U+")) {
				codestr = codestr.substring(2)
			}
			Integer code = Integer.parseInt(codestr, 16)
			String str = codeToStr(code)
			String conn = "✓";
			if (it.class == "optional") {
				conn = "✲"
			}
			if (it.class != "forbidden") {
				switch (it.category) {
				case "control":
				theChart.addCharacter(str, "control", language, conn)
				break
				case "common_punctuation":
				theChart.addCharacter(str, "punctuations", language, conn)
				break
				case "persian_punctuation":
				theChart.addCharacter(str, "punctuations", language, conn)
				break
				case "math":
				ChartCharacter ch = new ChartCharacter(str, "", language, conn)
				if (ch.isDigit()) {
					theChart.addCharacter(str, "numbers", language, conn)
				} else {
					theChart.addCharacter(str, "punctuations", language, conn)
				}
				break
				case "alphabet":
				theChart.addCharacter(str, "alphabet", language, conn)
				break
				case "subsidiary":
				theChart.addCharacter(str, "alphabet", language, conn)
				break
				case "diacritic":
				theChart.addCharacter(str, "diacritics", language, conn)
				break
				default:
				println("unknown category")
				}
			}
		}
	}

	private static String codeToStr(Integer code) {
		int[] a = [code.intValue()]
		return new String(a, 0, 1)
	}

	private void addUnknownCharacter(String str, String lang, String langConn) {
		ChartCharacter ch = new ChartCharacter(str, "", lang, langConn)
		if (ch.isLetter()) {
			theChart.addCharacter(str, "alphabet", lang, langConn)
		}
		if (ch.isDiacritic()) {
			theChart.addCharacter(str, "diacritics", lang, langConn)
		}
		if (ch.isDigit()) {
			theChart.addCharacter(str, "numbers", lang, langConn)
		}
		if (ch.isBidiControl()) {
			theChart.addCharacter(str, "control", lang, langConn)
		}
	}
}
