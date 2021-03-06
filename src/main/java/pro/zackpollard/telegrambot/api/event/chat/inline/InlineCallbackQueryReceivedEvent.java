package pro.zackpollard.telegrambot.api.event.chat.inline;

import pro.zackpollard.telegrambot.api.chat.inline.InlineCallbackQuery;
import pro.zackpollard.telegrambot.api.event.chat.CallbackQueryReceivedEvent;

/**
 * @author zackp
 */
public class InlineCallbackQueryReceivedEvent extends CallbackQueryReceivedEvent {

    public InlineCallbackQueryReceivedEvent(InlineCallbackQuery inlineCallbackQuery) {

        super(inlineCallbackQuery);
    }

    public InlineCallbackQuery getCallbackQuery() {

        return (InlineCallbackQuery) callbackQuery;
    }
}
