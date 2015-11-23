//package org.w3c.alreq

import com.ibm.icu.lang.UProperty
import com.ibm.icu.lang.UCharacter

/**
 * Created by shervinafshar on 11/21/15.
 */

// TODO:
// - CharBuffer support
// - Throw exception if str.size > 1 and not surrogate pair
// - Thrown exception if str.size > 2

public class UnicodeCharacter {

    private String cStr = ''
    private UCharacter uc = new UCharacter()
    private Integer cp

    public UnicodeCharacter (String cStr){

        this.cp = this.uc.codePointAt(cStr,0)
        this.cStr = cStr

    }

    public UnicodeCharacter (char c){
        this.cp = this.uc.getCodePoint(c)
    }

    public UnicodeCharacter (char c1, char c2){
        this.cp = this.uc.getCodePoint(c1, c2)
    }

    public UnicodeCharacter (UCharacter c){

        this.uc = c
        this.cStr = c.toString()

    }

    public String getString (){

        return this.cStr

    }

    public String getCodePointString (){

        return "U+" + Integer.toHexString(this.cp).toUpperCase()

    }

    public String getName (){

        return uc.getName(this.cp)

    }

    public Boolean isDiacritic (){

        return uc.hasBinaryProperty(this.cp, UProperty.DIACRITIC)

    }

    public Boolean isBidiControl (){

        return uc.hasBinaryProperty(this.cp, UProperty.BIDI_CONTROL)

    }

    public Boolean isLetter (){

        return uc.isLetter(this.cp)

    }

    public Boolean isDigit (){

        return (uc.isLetterOrDigit(this.cp) && !uc.isLetter(this.cp))

    }

}
