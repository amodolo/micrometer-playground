package dev.me.service3;

import dev.me.service3.service.KafkaClientService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
        Runtime.getRuntime().addShutdownHook(new Thread(main::shutdown));
    }

    private final KafkaClientService service;

    public Main() {
        this.service = new KafkaClientService();
    }

    void start() {
        service.subscribe(List.of("bean-created", "bean-updated", "bean-deleted"));
    }

    void shutdown() {
        service.shutdown();
    }
}
