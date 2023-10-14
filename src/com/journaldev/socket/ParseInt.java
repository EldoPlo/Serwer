package com.journaldev.socket;

public class ParseInt implements GenericParser {

    @Override
    public Object parse(String string)
    {
        return Integer.parseInt(string);
    }
}
