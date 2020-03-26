package com.finaltest.authentication.token;

import java.text.NumberFormat;
import java.text.ParseException;

public class test {

    public static void main(String[] args) {
        try{
            int bruto = 40000;
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            Number value = numberFormat.parse("50%");
            System.out.println(value.floatValue());
            float hasil = bruto * value.floatValue() ;
            System.out.println((int) hasil);
        } catch (ParseException er) {
            return;
        }
    }
}
