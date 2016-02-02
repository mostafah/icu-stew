import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.text.DecimalFormatSymbols
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
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
			addCharacters(it)
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

    private void addCharacters(String language){
		ULocale u = new ULocale(language)
        LocaleData ud = new LocaleData().getInstance(u)

		// add alphabets and diacritics
        UnicodeSet usEsStd = ud.getExemplarSet(0, ud.ES_STANDARD)
        UnicodeSet usEsAux = ud.getExemplarSet(0, ud.ES_AUXILIARY)
		usEsStd.iterator().each { addUnknownCharacter(it, language, "•") }
		usEsAux.iterator().each { addUnknownCharacter(it, language, "x") }

		// add punctuations
        UnicodeSet usEsPun = ud.getExemplarSet(0, ud.ES_PUNCTUATION)
		usEsPun.iterator().each { punctuations.addCharacter(it, language, "•") }

		// add numbers
		DecimalFormatSymbols numSyms = new DecimalFormatSymbols(u)
		numSyms.getDigits().each { numbers.addCharacter(it, language,  "•") }
    }

	private void addUnknownCharacter(String str, String lang, String langCon) {
		ChartCharacter ch = new ChartCharacter(str, lang, langCon)
		if (ch.isLetter()) {
			alphabet.addCharacter(str, lang, langCon)
		}
		if (ch.isDiacritic()) {
			diacritics.addCharacter(str, lang, langCon)
		}
	}
}
