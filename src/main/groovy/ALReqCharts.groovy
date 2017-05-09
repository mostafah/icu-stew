import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.text.DecimalFormatSymbols
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import groovy.json.JsonSlurper
import au.com.bytecode.opencsv.CSVWriter;

class ALReqCharts {
	Chart alphabet = new Chart()
	Chart diacritics = new Chart()
	Chart numbers = new Chart()
	Chart punctuations = new Chart()
	Chart control = new Chart()

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
		alphabet.exportCSV("Alphabet.csv")
		diacritics.exportCSV("Diacritics.csv")
		numbers.exportCSV("Numbers.csv")
		punctuations.exportCSV("Punctuations.csv")
		control.exportCSV("Control.csv")
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
		usEsPun.iterator().each { punctuations.addCharacter(it, language, "✓") }

		// add numbers
		DecimalFormatSymbols numSyms = new DecimalFormatSymbols(u)
		numSyms.getDigits().each { numbers.addCharacter(it, language,  "✓") }

		addNumberSymbols(language)
    }

	private void addNumberSymbols(String language) {
		ULocale u = new ULocale(language)
		DecimalFormatSymbols numSyms = new DecimalFormatSymbols(u)
		punctuations.addCharacter(numSyms.getDecimalSeparator(), language,  "✓")
		punctuations.addCharacter(numSyms.getGroupingSeparator(), language,  "✓")
		punctuations.addCharacter(numSyms.getPercent(), language,  "✓")
		punctuations.addCharacter(numSyms.getMinusSign(), language,  "✓")
		punctuations.addCharacter(numSyms.getPlusSign(), language,  "✓")
		punctuations.addCharacter(numSyms.getExponentMultiplicationSign(), language,  "✓")
		punctuations.addCharacter(numSyms.getInfinity(), language,  "✓")
	}

	private void addCLDRCharacters(String language) {
		CLDRLocale l = new CLDRLocale(language)
		l.getExemplarCharacters().each() { addUnknownCharacter(it, language, "✓") }
		l.getAuxiliaryCharacters().each() { addUnknownCharacter(it, language, "✲") }
		l.getPunctuations().each() { punctuations.addCharacter(it, language, "✓") }
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
				control.addCharacter(str, language, conn)
				break
				case "common_punctuation":
				punctuations.addCharacter(str, language, conn)
				break
				case "persian_punctuation":
				punctuations.addCharacter(str, language, conn)
				break
				case "math":
				ChartCharacter ch = new ChartCharacter(str, language, conn)
				if (ch.isDigit()) {
					numbers.addCharacter(str, language, conn)
				} else {
					punctuations.addCharacter(str, language, conn)
				}
				break
				case "alphabet":
				alphabet.addCharacter(str, language, conn)
				break
				case "subsidiary":
				alphabet.addCharacter(str, language, conn)
				break
				case "diacritic":
				diacritics.addCharacter(str, language, conn)
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
		ChartCharacter ch = new ChartCharacter(str, lang, langConn)
		if (ch.isLetter()) {
			alphabet.addCharacter(str, lang, langConn)
		}
		if (ch.isDiacritic()) {
			diacritics.addCharacter(str, lang, langConn)
		}
		if (ch.isDigit()) {
			numbers.addCharacter(str, lang, langConn)
		}
		if (ch.isBidiControl()) {
			control.addCharacter(str, lang, langConn)
		}
	}
}
