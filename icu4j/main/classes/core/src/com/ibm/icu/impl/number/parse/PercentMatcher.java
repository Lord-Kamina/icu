// © 2017 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.DecimalFormatSymbols;

/**
 * @author sffc
 *
 */
public class PercentMatcher extends SymbolMatcher {

    private static final PercentMatcher DEFAULT = new PercentMatcher();

    public static PercentMatcher getInstance(DecimalFormatSymbols symbols) {
        String symbolString = symbols.getPercentString();
        if (DEFAULT.uniSet.contains(symbolString)) {
            return DEFAULT;
        } else {
            return new PercentMatcher(symbolString);
        }
    }

    private PercentMatcher(String symbolString) {
        super(symbolString, DEFAULT.uniSet);
    }

    private PercentMatcher() {
        super(UnicodeSetStaticCache.Key.PERCENT_SIGN);
    }

    @Override
    protected boolean isDisabled(ParsedNumber result) {
        return 0 != (result.flags & ParsedNumber.FLAG_PERCENT);
    }

    @Override
    protected void accept(StringSegment segment, ParsedNumber result) {
        result.flags |= ParsedNumber.FLAG_PERCENT;
        result.setCharsConsumed(segment);
    }

    @Override
    public void postProcess(ParsedNumber result) {
        super.postProcess(result);
        if (0 != (result.flags & ParsedNumber.FLAG_PERCENT) && result.quantity != null) {
            result.quantity.adjustMagnitude(-2);
        }
    }

    @Override
    public String toString() {
        return "<PercentMatcher>";
    }
}