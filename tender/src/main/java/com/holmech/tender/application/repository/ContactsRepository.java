package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Contacts;
import org.springframework.data.repository.CrudRepository;

public interface ContactsRepository extends CrudRepository<Contacts, Long> {
    Contacts findByPhone(String phone);
}
