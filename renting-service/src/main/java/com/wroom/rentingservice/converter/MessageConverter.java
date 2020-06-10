package com.wroom.rentingservice.converter;

import com.wroom.rentingservice.domain.Message;
import com.wroom.rentingservice.domain.dto.MessageDTO;

public class MessageConverter extends AbstractConverter {

	public static MessageDTO fromEntity(Message entity) {
        return new MessageDTO(
                entity.getToUserId(),
                entity.getRentRequestId(),
                entity.getTitle(),
                entity.getContent()
        );
    }

    public static Message toEntity(MessageDTO dto) {
    	return new Message(
    			null, 
    			null, 
    			dto.getToUserId(), 
    			dto.getRentRequestId(), 
    			dto.getTitle(), 
    			dto.getContent());
    }
	
}