package org.telegram.botapi.api.internal.chat.message.content;

import org.telegram.botapi.api.chat.message.content.TextContent;

/**
 * @author Zack Pollard
 */
public class TextContentImpl implements TextContent {

    private final String content;

    private TextContentImpl(String text) {

        this.content = text;
    }

    public static TextContent createTextContent(String text) {

        return text != null ? new TextContentImpl(text) : null;
    }

    @Override
    public String getContent() {

        return content;
    }
}
