package com.messages.redisApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;

@Data
@Entity
public class Message implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String message;

    public Message( ) {

    }

    public Message(String message) {
        this.message = message;
    }
}

