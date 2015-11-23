import com.ibm.icu.util.ULocale
import com.ibm.icu.text.DateFormat
import com.ibm.icu.util.LocaleData
import com.ibm.icu.text.UnicodeSet

/*
 * @author shervinafshar, @date 11/21/15 5:24 PM
 */


public class Stew {

    public static void main(String[] args){


        List arabWorld = ['EG', 'DZ', 'SA', 'IQ', 'SD', 'MA', \
                  'YE', 'SY', 'TN', 'JO', 'LB', 'LY', \
                  'PS', 'AE', 'SO', 'SS', 'MR', 'KW', \
                  'OM', 'TD', 'QA', 'IL', 'BH', 'EH', \
                  'KM', 'TR', 'ER', 'ML', 'NG', 'CM', \
                  'DJ', 'NE', 'KE', 'CY', 'TJ']

        ULocale u1 = new ULocale('ar')
        LocaleData ud = new LocaleData()
        LocaleData ud1 = ud.getInstance(u1)
        UnicodeSet esStrd1 = ud1.getExemplarSet(0, ud.ES_STANDARD)

        for (t in arabWorld){
            ULocale u2 = new ULocale("ar-${t}")
            LocaleData ud2 = ud.getInstance(u2)
            UnicodeSet esStrd2 = ud2.getExemplarSet(0, ud.ES_STANDARD)

            if (!esStrd2.equals(esStrd1)){
                println t
            }else{
                String l = esStrd2.iterator().collect { it }
                println "${t}: ${l}"
            }
        }

        for (c in esStrd1.iterator()){

            UnicodeCharacter uc = new UnicodeCharacter(c)

            println uc.codePointString
            println uc.getName()
            println uc.isLetter()
            println uc.isDiacritic()
            println uc.isDigit()


        }


    }

}
