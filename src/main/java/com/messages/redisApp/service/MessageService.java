package com.messages.redisApp.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.messages.redisApp.cache.CacheClient;
import com.messages.redisApp.model.Message;
import com.messages.redisApp.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

	 private final MessageRepository repository;
	 private final CacheClient<Message> cache;
	 
	 public List<Message> getMessages(){
		 return this.repository.findAll();
	 }
	 
	 public Message getMessageById(Integer id){
		 try {
		 if(id.intValue()==0) {
			 throw new Exception("id invalido");
		 }
		 Message m = cache.recover(id.toString(), Message.class);
		 if(!Objects.isNull(m)) {
			 return m;
		 }
		 return repository.findById(id).orElseThrow( ()-> new Exception("No message found with id: "+ id.toString()));
		 }
		 catch(JsonProcessingException e) {
			 log.error("Error converting message to string", e);
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
	 public Message create(Message m) {
		 try {
	            Message data = repository.save(m);
	            return saveMessageInCache(data);
	        } catch (JsonProcessingException e) {
	            log.error("Error converting message to string", e);
	        }
	        return m;
	 }
	 private Message saveMessageInCache(Message m) throws JsonProcessingException{
		 return cache.save(m.getId().toString(), m);
	 }
}
