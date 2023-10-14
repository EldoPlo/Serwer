package com.journaldev.socket;

public class ParseDouble implements GenericParser
{

    @Override
    public Object parse(String string)
    {
        return Double.parseDouble(string);
    }
}
