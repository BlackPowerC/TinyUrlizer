package com.blackpowerc.tinyurlizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Service
public class ShrinkedURLService
{
    private ShrinkedURLRepository repo ;

    @Autowired
    public ShrinkedURLService(ShrinkedURLRepository repo) {
        this.repo = repo;
    }

    /**
     * </p>
     *  Enregistrer une nouvelle url raccourcie.<br/>
     *  Si une URL raccourcie existe déjà son entité est renvoyée. Sinon, il y a insertion.
     * </p>
     *
     * @param sourceUrl L'url à raccourcir.
     * @return Une instance de ShrinkedURL.
     */
    public ShrinkedURL shrinkUrl(String sourceUrl)
    {
        Objects.requireNonNull(sourceUrl, "Source URL must be not null") ;

        var optionalShrinked = this.repo.findBySourceUrl(sourceUrl) ;
        if(optionalShrinked.isPresent()) {
            return optionalShrinked.get() ;
        }

        ShrinkedURL newShrinked = new ShrinkedURL() ;
        newShrinked.setSourceUrl(sourceUrl) ;

        return this.repo.saveAndFlush(newShrinked) ;
    }

    public Optional<ShrinkedURL> findByPartialId(String partialUuid)
    {
        var probe = new ShrinkedURL() ;
        probe.setId(partialUuid) ;

        return this.repo.findOne(
                Example.of(probe,
                        ExampleMatcher.matching().withMatcher("id",
                                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.STARTING)
                        )
                )
        ) ;

    }
}
