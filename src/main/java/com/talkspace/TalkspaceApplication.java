package com.talkspace;

import com.talkspace.account.infra.socket.SocketIOAdapter;
import com.talkspace.account.infra.socket.SocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class TalkspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkspaceApplication.class, args);
	}

	@Bean
	@Primary
	public SocketServer socketServer() {
		return new SocketIOAdapter();
	}

	@Bean
	public CommandLineRunner commandLineRunner(SocketServer socketServer) {
		return args -> {
			socketServer.start();
			Runtime.getRuntime().addShutdownHook(new Thread(socketServer::stop));
		};
	}

}
