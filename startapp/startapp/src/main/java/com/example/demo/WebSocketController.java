package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebSocketController {
    @MessageMapping("/updateTable")
    @SendTo("/topic/tableUpdates")
    public String sendTableUpdate(String message) {
        // Tento příklad prostě přeposílá zprávu, kterou obdrží.
        // Zde byste mohli získat nová data, která chcete zobrazit, a vrátit je.
        return message;
    }
}
