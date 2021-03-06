package pro.zackpollard.telegrambot.api.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.body.MultipartBody;
import org.json.JSONException;
import org.json.JSONObject;
import pro.zackpollard.telegrambot.api.TelegramBot;
import pro.zackpollard.telegrambot.api.chat.message.ForceReply;
import pro.zackpollard.telegrambot.api.chat.message.send.NotificationOptions;
import pro.zackpollard.telegrambot.api.chat.message.send.ReplyingOptions;
import pro.zackpollard.telegrambot.api.keyboards.InlineKeyboardMarkup;
import pro.zackpollard.telegrambot.api.keyboards.ReplyKeyboardHide;
import pro.zackpollard.telegrambot.api.keyboards.ReplyKeyboardMarkup;

import java.util.Random;

/**
 * @author Zack Pollard
 */
public class Utils {

    public static String generateRandomString(int length) {

        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }

    public static JSONObject processResponse(HttpResponse<String> response) {

        if (response != null) {

            if (response.getStatus() == 200) {

                try {

                    return new JSONObject(response.getBody());
                } catch (JSONException e) {

                    System.err.println("The API didn't return a JSON response. The actual response was " + response.getBody());
                }
            } else {

                JSONObject jsonResponse = null;

                try {

                    jsonResponse = new JSONObject(response.getBody());
                } catch (JSONException e) {
                }

                if (jsonResponse != null) {

                    System.err.println("The API returned the following error: " + jsonResponse.getString("description"));
                } else {

                    System.err.println("The API returned error code " + response.getStatus());
                }
            }
        }

        return null;
    }

    public static void processReplyContent(MultipartBody multipartBody, ReplyingOptions replyingOptions) {

        if (replyingOptions.getReplyTo() != 0)
            multipartBody.field("reply_to_message_id", String.valueOf(replyingOptions.getReplyTo()), "application/json");
        if (replyingOptions.getReplyMarkup() != null) {

            switch (replyingOptions.getReplyMarkup().getType()) {

                case FORCE_REPLY:
                    multipartBody.field("reply_markup", TelegramBot.GSON.toJson(replyingOptions.getReplyMarkup(), ForceReply.class), "application/json");
                    break;
                case KEYBOARD_HIDE:
                    multipartBody.field("reply_markup", TelegramBot.GSON.toJson(replyingOptions.getReplyMarkup(), ReplyKeyboardHide.class), "application/json");
                    break;
                case KEYBOARD_MARKUP:
                    multipartBody.field("reply_markup", TelegramBot.GSON.toJson(replyingOptions.getReplyMarkup(), ReplyKeyboardMarkup.class), "application/json");
                    break;
                case INLINE_KEYBOARD_MARKUP:
                    multipartBody.field("reply_markup", TelegramBot.GSON.toJson(replyingOptions.getReplyMarkup(), InlineKeyboardMarkup.class), "application/json");
                    break;
            }
        }
    }

    public static void processNotificationContent(MultipartBody multipartBody, NotificationOptions notificationOptions) {

        multipartBody.field("disable_notification", notificationOptions.isDisableNotification());
    }

    public static boolean checkResponseStatus(JSONObject jsonResponse) {

        if (jsonResponse != null) {

            if (jsonResponse.getBoolean("ok")) {

                return true;
            } else {

                System.err.println("The API returned the following error: " + jsonResponse.getString("description"));
            }
        } else {

            System.err.println("JSON Response was null, something went wrong...");
        }

        return false;
    }
}
