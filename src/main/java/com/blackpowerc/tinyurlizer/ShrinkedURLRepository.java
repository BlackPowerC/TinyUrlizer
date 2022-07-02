package com.blackpowerc.tinyurlizer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Repository
public interface ShrinkedURLRepository extends JpaRepository<ShrinkedURL, String> {
    Optional<ShrinkedURL> findBySourceUrl(String sourceUrl) ;
}
