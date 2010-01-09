/*
 * $Id$
 * 
 * Copyright 2010 Hiroki Ata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aexlib.util;

import java.util.Enumeration;

/**
 * @author hata
 *
 */
public class QuotedStringTokenizer implements Enumeration<String> {
    /** default delim characters */
    public static final String DEFAULT_DELIM = " \t\n\r\f";

    /** a default quote character */
    public static final String DEFAULT_QUOTE_TEXT = "\"";

    /** a default flag which return delim characters or not. */
    public static final boolean DEFAULT_DELIM_RETURN_FLAG = false;

    /** passed quote text */
    private String m_quoteText;

    /** tokenized text */
    private String m_str;
    
    /** delim characters */
    private String m_delim;
    
    /** a flag to return delim characters or not. */
    private boolean m_returnDelims;
    
    /** a seek index. */
    private int m_currentIndex;

    /** a flag to return quote text or not. */
    private boolean m_returnQuote;

    /**
     * Constructor.
     * 
     * @param str is tokened text.
     */
    public QuotedStringTokenizer(String str) {
        this(str, DEFAULT_DELIM, DEFAULT_QUOTE_TEXT);
    }

    /**
     * Constructor.
     * 
     * @param str is tokened text.
     * @param quoteText is a quoted text such as '"'.
     */
    public QuotedStringTokenizer(String str, String quoteText) {
        this(str, DEFAULT_DELIM, quoteText);
    }

    
    /**
     * Constructor.
     * 
     * @param str is tokened text.
     * @param delim is a delimiter string such as " \t\n\r"
     * @param quoteText is a quoted text such as '"'.
     */
    public QuotedStringTokenizer(String str, String delim, String quoteText) {
        this(str, delim, DEFAULT_DELIM_RETURN_FLAG, quoteText);
    }
    

    /**
     * Constructor.
     * 
     * @param str is tokened text.
     * @param delim is a delimiter string such as " \t\n\r"
     * @param returnDelims is a flag to return delim characters or not.
     * @param quoteText is a quoted text such as '"'.
     */
    public QuotedStringTokenizer(String str, String delim, boolean returnDelims, String quoteText) {
        this(str, delim, returnDelims, quoteText, false);
    }
    

    /**
     * Constructor.
     * 
     * @param str is tokened text.
     * @param delim is a delimiter string such as " \t\n\r"
     * @param returnDelims is a flag to return delim characters or not.
     * @param quoteText is a quoted text such as '"'.
     * @param returnQuote is a flag to return quoteText or not.
     */
    public QuotedStringTokenizer(String str, String delim, boolean returnDelims,
            String quoteText, boolean returnQuote) {
        m_str = str;
        m_delim = delim;
        m_returnDelims = returnDelims;
        m_currentIndex = 0;
        m_quoteText = quoteText;
        m_returnQuote = returnQuote;
    }
    
    
    /**
     * Get the number of tokens.
     * @return the total number of tokens.
     */
    public int countTokens() {
        int count = 0;
        StringBuffer buff = new StringBuffer();
        int dummyCurrentIndex = nextToken(m_str, m_currentIndex, m_delim, m_returnDelims, buff);
        while (dummyCurrentIndex != -1) {
            count++;
            dummyCurrentIndex = nextToken(m_str, dummyCurrentIndex, m_delim, m_returnDelims, buff);
        }
        return count;
    }

    
    /**
     * Get information there is any tokens or not.
     * @return true if there are one or more tokens, otherwise false.
     */
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    /**
     * Get information there is any tokens or not.
     * @return true if there are one or more tokens, otherwise false.
     */
    public boolean hasMoreTokens() {
        StringBuffer buff = new StringBuffer();
        return (nextToken(m_str, m_currentIndex, m_delim, m_returnDelims, buff) != -1);
    }

    
    /**
     * Get a next token.
     * @return a next token.
     */
    public String nextElement() {
        return nextToken();
    }

    /**
     * Get a next token.
     * @return a next token.
     */
    public String nextToken() {
        return nextToken(m_delim);
    }


    /**
     * Get nextToken using the argument's delimiter character.
     * @param delim is the new character of delim.
     * @return a next token.
     */
    public String nextToken(String delim) {
        StringBuffer buff = new StringBuffer();
        m_currentIndex = nextToken(m_str, m_currentIndex, delim, m_returnDelims, buff);
        if (-1 < m_currentIndex)
            return new String(buff);
        else
            return null;
    }

    
    /**
     * Get a next token.
     * @param str is a seeked string.
     * @param currentIndex is a start index to get a token.
     * @param delim is a delimitar text.
     * @param returnDelims is a flag to return delim characters or not.
     * @param buff is a buffer to return a token.
     * @return an index which is the place of a next token.
     */
    protected int nextToken(
        String str,
        int currentIndex,
        String delim,
        boolean returnDelims,
        StringBuffer buff // return string
        )
    {
        if (delim == null)
            return -1;

        boolean tokenFound = false;
        boolean escaped = false;

        for (int i = currentIndex;i < str.length();i++) {
            if (delim.indexOf(str.charAt(i)) != -1 && (!escaped)) {
                // found delim char
                if (tokenFound) {
                    return i;
                } else if (returnDelims) {
                    buff.append(str.substring(i, i+1));
                    return i + 1;
                } else {
                    // do nothing because the delim
                    // char will go to trash.
                }
            } else if (m_quoteText.indexOf(str.charAt(i)) != -1) {
                if (0 < i && str.charAt(i - 1) == '\\') {
                    if (0 < buff.length()) {
                        buff.deleteCharAt(buff.length() -1);
                    }
                    buff.append(str.charAt(i));
                } else {
                    escaped = !escaped;
                    if (m_returnQuote) {
                        buff.append(str.charAt(i));
                        tokenFound = true;
                    }
                }
            } else {
                // didn't find delim char.
                buff.append(str.charAt(i));
                tokenFound = true;
            }
        }

        if (tokenFound)
            return str.length();
        else
            return -1;
    }
}
