import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import org.junit.Test

/**
 * Created by shervinafshar on 11/22/15.
 */

class ExemplarTests {

    @Test
    // If any of Arabic sub-locales have any different exemplar set
    void exemplarDisparityTest() {

        List arabWorld = ['EG', 'DZ', 'SA', 'IQ', 'SD', 'MA', \
                  'YE', 'SY', 'TN', 'JO', 'LB', 'LY', \
                  'PS', 'AE', 'SO', 'SS', 'MR', 'KW', \
                  'OM', 'TD', 'QA', 'IL', 'BH', 'EH', \
                  'KM', 'TR', 'ER', 'ML', 'NG', 'CM', \
                  'DJ', 'NE', 'KE', 'CY', 'TJ']

        ULocale u1 = new ULocale('ar')
        LocaleData ud1 = new LocaleData().getInstance(u1)
        UnicodeSet usStrd1 = ud1.getExemplarSet(0, ud1.ES_STANDARD)
        UnicodeSet usAux1  = ud1.getExemplarSet(0, ud1.ES_AUXILIARY)
        UnicodeSet usPunc1  = ud1.getExemplarSet(0, ud1.ES_PUNCTUATION)

        for (t in arabWorld){
            ULocale u2 = new ULocale("ar-${t}")
            LocaleData ud2 = new LocaleData().getInstance(u2)
            UnicodeSet usStrd2 = ud2.getExemplarSet(0, ud2.ES_STANDARD)
            UnicodeSet usAux2  = ud2.getExemplarSet(0, ud2.ES_AUXILIARY)
            UnicodeSet usPunc2  = ud2.getExemplarSet(0, ud2.ES_PUNCTUATION)

            assert usStrd2.equals(usStrd1) == true
            assert usAux2.equals(usAux1) == true
            assert usPunc2.equals(usPunc1) == true

        }

    }

}
