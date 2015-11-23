import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by shervinafshar on 11/21/15.
 */
class Chart {

    public static void generateChart(ULocale u){

        LocaleData ud = new LocaleData().getInstance(u)
        UnicodeSet usEsStd = ud.getExemplarSet(0, ud.ES_STANDARD)
        UnicodeSet usEsAux = ud.getExemplarSet(0, ud.ES_AUXILIARY)
        UnicodeSet usEsPun = ud.getExemplarSet(0, ud.ES_PUNCTUATION)

        FileWriter fw = new FileWriter("Exemplar-Chart-${u.displayLanguage}.csv")
        CSVWriter writer = new CSVWriter(fw);

        Collection<UnicodeSet> usList = []

        usList.add(usEsStd)
        usList.add(usEsAux)
        usList.add(usEsPun)

        String[] header = ["Unicode Name", "Code Point", "Character",
                           "Is Letter?", "Is Digit?", "Is Diacritic?",
                           "Is Bidi Control?"]

        writer.writeNext(header)

        usList.each {
            it.iterator().each {

                UnicodeCharacter uc = new UnicodeCharacter(it)
                String[] ucValues = [uc.getName(), uc.codePointString, uc.getString(),
                                     uc.isLetter(), uc.isDigit(), uc.isDiacritic(),
                                     uc.isBidiControl()]
                writer.writeNext(ucValues)
            }
        }

        writer.close()

    }
}