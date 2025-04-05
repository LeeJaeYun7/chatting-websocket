package com.example.chatting_websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ChattingWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattingWebsocketApplication.class, args);
	}

}
