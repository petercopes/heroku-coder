package com.messages.redisApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.messages.redisApp.model.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
