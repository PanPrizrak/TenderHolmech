package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
