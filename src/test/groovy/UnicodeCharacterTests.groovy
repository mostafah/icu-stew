import com.ibm.icu.text.UnicodeSet
import org.junit.Test

/**
 * Created by shervinafshar on 11/22/15.
 */

class UnicodeCharacterTests {

    @Test
    void codePointStringTest(){
        UnicodeCharacter uc = new UnicodeCharacter('ر')
        assert uc.codePointString == 'U+631'
    }

    @Test
    void symbolsTest(){
        String b = '\ud83d\ude00'
        UnicodeCharacter uc3 = new UnicodeCharacter(b)
        assert uc3.getName() == 'GRINNING FACE'
        assert uc3.codePointString == 'U+1F600'

        char c = '\ud83d'
        char d = '\ude00'
        UnicodeCharacter uc4 = new UnicodeCharacter(c,d)
        assert uc4.getName() == 'GRINNING FACE'
    }

    @Test
    void nameTest(){

        UnicodeSet us = new UnicodeSet().fromAll('ݱﴋ۞ࢧ')

        us.iterator().each {
            UnicodeCharacter uc = new UnicodeCharacter(it)
            assert uc.getName().count('ARABIC') > 0

        }

    }

    @Test
    void letterPropsTest(){

        UnicodeCharacter uc = new UnicodeCharacter('ر')

        assert [uc.isLetter(), uc.isDigit(), uc.isDiacritic(), uc.isBidiControl()] \
                == [true, false, false, false]

    }

    @Test
    void numberPropsTest(){

        UnicodeCharacter uc = new UnicodeCharacter('۷')

        assert [uc.isLetter(), uc.isDigit(), uc.isDiacritic(), uc.isBidiControl()] \
                == [false, true, false, false]

    }

    @Test
    void diacriticPropsTest(){

        UnicodeCharacter uc = new UnicodeCharacter('ُ')

        assert [uc.isLetter(), uc.isDigit(), uc.isDiacritic(), uc.isBidiControl()] \
                == [false, false, true, false]


    }

    @Test
    void symbolPropsTest(){

        char l = '\ud83d'
        char h = '\ude00'

        UnicodeCharacter uc = new UnicodeCharacter(l, h)

        assert [uc.isLetter(), uc.isDigit(), uc.isDiacritic(), uc.isBidiControl()] \
                == [false, false, false, false]

    }

}
