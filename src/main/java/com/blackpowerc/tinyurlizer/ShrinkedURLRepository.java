package com.blackpowerc.tinyurlizer;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Repository
public interface ShrinkedURLRepository extends JpaRepository<ShrinkedURL, UUID> {
    Optional<ShrinkedURL> findBySourceUrl(String sourceUrl) ;
}
