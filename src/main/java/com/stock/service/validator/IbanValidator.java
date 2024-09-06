package com.stock.service.validator;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IbanValidator {
    private static final int IBAN_MIN_LENGTH = 15;
    private static final int IBAN_MAX_LENGTH = 34;

    private static final Map<String, Integer> COUNTRY_IBAN_LENGTH = new HashMap<>();

    static {
        COUNTRY_IBAN_LENGTH.put("AL", 28);
        COUNTRY_IBAN_LENGTH.put("AD", 24);
        COUNTRY_IBAN_LENGTH.put("AT", 20);
        COUNTRY_IBAN_LENGTH.put("AZ", 28);
        COUNTRY_IBAN_LENGTH.put("BH", 22);
        COUNTRY_IBAN_LENGTH.put("BE", 16);
        COUNTRY_IBAN_LENGTH.put("BA", 20);
        COUNTRY_IBAN_LENGTH.put("BR", 29);
        COUNTRY_IBAN_LENGTH.put("BG", 22);
        COUNTRY_IBAN_LENGTH.put("CR", 22);
        COUNTRY_IBAN_LENGTH.put("HR", 21);
        COUNTRY_IBAN_LENGTH.put("CY", 28);
        COUNTRY_IBAN_LENGTH.put("CZ", 24);
        COUNTRY_IBAN_LENGTH.put("DK", 18);
        COUNTRY_IBAN_LENGTH.put("DO", 28);
        COUNTRY_IBAN_LENGTH.put("EE", 20);
        COUNTRY_IBAN_LENGTH.put("FI", 18);
        COUNTRY_IBAN_LENGTH.put("FR", 27);
        COUNTRY_IBAN_LENGTH.put("GE", 22);
        COUNTRY_IBAN_LENGTH.put("DE", 22);
        COUNTRY_IBAN_LENGTH.put("GI", 23);
        COUNTRY_IBAN_LENGTH.put("GR", 27);
        COUNTRY_IBAN_LENGTH.put("GT", 28);
        COUNTRY_IBAN_LENGTH.put("HU", 28);
        COUNTRY_IBAN_LENGTH.put("IS", 26);
        COUNTRY_IBAN_LENGTH.put("IE", 22);
        COUNTRY_IBAN_LENGTH.put("IL", 23);
        COUNTRY_IBAN_LENGTH.put("IT", 27);
        COUNTRY_IBAN_LENGTH.put("JO", 30);
        COUNTRY_IBAN_LENGTH.put("KZ", 20);
        COUNTRY_IBAN_LENGTH.put("XK", 20);
        COUNTRY_IBAN_LENGTH.put("KW", 30);
        COUNTRY_IBAN_LENGTH.put("LV", 21);
        COUNTRY_IBAN_LENGTH.put("LB", 28);
        COUNTRY_IBAN_LENGTH.put("LI", 21);
        COUNTRY_IBAN_LENGTH.put("LT", 20);
        COUNTRY_IBAN_LENGTH.put("LU", 20);
        COUNTRY_IBAN_LENGTH.put("MT", 31);
        COUNTRY_IBAN_LENGTH.put("MR", 27);
        COUNTRY_IBAN_LENGTH.put("MU", 30);
        COUNTRY_IBAN_LENGTH.put("MD", 24);
        COUNTRY_IBAN_LENGTH.put("MC", 27);
        COUNTRY_IBAN_LENGTH.put("ME", 22);
        COUNTRY_IBAN_LENGTH.put("NL", 18);
        COUNTRY_IBAN_LENGTH.put("MK", 19);
        COUNTRY_IBAN_LENGTH.put("NO", 15);
        COUNTRY_IBAN_LENGTH.put("PK", 24);
        COUNTRY_IBAN_LENGTH.put("PS", 29);
        COUNTRY_IBAN_LENGTH.put("PL", 28);
        COUNTRY_IBAN_LENGTH.put("PT", 25);
        COUNTRY_IBAN_LENGTH.put("QA", 29);
        COUNTRY_IBAN_LENGTH.put("RO", 24);
        COUNTRY_IBAN_LENGTH.put("SM", 27);
        COUNTRY_IBAN_LENGTH.put("SA", 24);
        COUNTRY_IBAN_LENGTH.put("RS", 22);
        COUNTRY_IBAN_LENGTH.put("SK", 24);
        COUNTRY_IBAN_LENGTH.put("SI", 19);
        COUNTRY_IBAN_LENGTH.put("ES", 24);
        COUNTRY_IBAN_LENGTH.put("SE", 24);
        COUNTRY_IBAN_LENGTH.put("CH", 21);
        COUNTRY_IBAN_LENGTH.put("TN", 24);
        COUNTRY_IBAN_LENGTH.put("TR", 26);
        COUNTRY_IBAN_LENGTH.put("UA", 29);
        COUNTRY_IBAN_LENGTH.put("AE", 23);
        COUNTRY_IBAN_LENGTH.put("GB", 22);
        COUNTRY_IBAN_LENGTH.put("VG", 24);
    }

    public static boolean validateIban(String iban) {
        if (iban == null || iban.isEmpty()) {
            return false;
        }

        iban = iban.replaceAll("\\s+", "");

        if (iban.length() < IBAN_MIN_LENGTH || iban.length() > IBAN_MAX_LENGTH) {
            return false;
        }

        String countryCode = iban.substring(0, 2).toUpperCase(Locale.ROOT);
        Integer expectedLength = COUNTRY_IBAN_LENGTH.get(countryCode);
        if (expectedLength == null || iban.length() != expectedLength) {
            return false;
        }

        return checkIbanUsingMod97(iban);
    }


    private static boolean checkIbanUsingMod97(String iban) {
        String reformattedIban = iban.substring(4) + iban.substring(0, 4);

        StringBuilder numericIban = new StringBuilder();
        for (char c : reformattedIban.toCharArray()) {
            if (Character.isDigit(c)) {
                numericIban.append(c);
            } else if (Character.isLetter(c)) {
                numericIban.append(Character.getNumericValue(c));
            } else {
                return false;
            }
        }

        BigInteger ibanNumber = new BigInteger(numericIban.toString());
        return ibanNumber.mod(BigInteger.valueOf(97)).intValue() == 1;
    }
}
