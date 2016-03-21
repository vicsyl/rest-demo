package org.virutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virutor.domain.EmailPreferenceItem;

public interface EmailPreferenceItemRepository extends JpaRepository<EmailPreferenceItem, Long> {

}
