package com.messages.redisApp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.messages.redisApp.model.Message;
import com.messages.redisApp.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;
    @GetMapping("/mensajes")
    public List<Message> getMensajes() {
        log.info("GET obtener mensajes");
        return service.getMessages();
    }

    @GetMapping("/mensajes/{id}")
    public Message getMensajeById(@PathVariable Integer id){
        log.info("GET obtener mensaje por el id");
        return service.getMessageById(id);
    }

    @PostMapping("/mensajes")
    public Message createMessage(@RequestBody Message message) {
        log.info("POST crear mensaje");
        return service.create(message);
    }

}