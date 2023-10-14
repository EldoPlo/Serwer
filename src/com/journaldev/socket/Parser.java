package com.journaldev.socket;

public class Parser<T>
{
    private final GenericParser parser;
    Parser(GenericParser parser)
    {
        this.parser = parser;
    }
    @SuppressWarnings("unchecked")
    public T parseValue(String s)
    {
        try
        {
            return (T)parser.parse(s);
        }
        catch (NumberFormatException e )
        {
            return null;
        }

    }
}
