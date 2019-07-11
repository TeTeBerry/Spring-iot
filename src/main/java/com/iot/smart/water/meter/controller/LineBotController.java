package com.iot.smart.water.meter.controller;


import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class LineBotController {
    final LineMessagingClient client = LineMessagingClient
            .builder("<channel access token>")
            .build();


    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        final LineMessagingClient client = LineMessagingClient
                .builder("dRaF1mGY8pDpxRStCA7sKp+HP4CcI/t7tTH0N8S98mz1r3b7SJN3UmJHG77hsaiWMe+MTCebi3Df355ZBTPpFAliAlJN2R6hhviDlpWAmvzeLn+9VMsjkzv0su8WHUf1wMz/Aeana12Xy2binCTVzAdB04t89/1O/w1cDnyilFU=")
                .build();

        final TextMessage textMessage = new TextMessage("hello");
        final PushMessage pushMessage = new PushMessage(
                "<to>",
                textMessage);

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = client.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new TextMessage(event.getMessage().getText());
        }

        System.out.println(botApiResponse);

        return new TextMessage(event.getMessage().getText());
    }
}
