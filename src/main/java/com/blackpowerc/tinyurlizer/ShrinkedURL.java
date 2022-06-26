package com.blackpowerc.tinyurlizer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entité représentant une URL raccourcie.
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@ToString
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "shrinked_url")
public class ShrinkedURL
{
    @Id
    @Getter @Setter
    private String id ;

    @Getter @Setter
    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt ;


    @URL
    @NotBlank
    @Getter @Setter
    @Column(name = "source_url", nullable = false)
    private String sourceUrl ;

    /**
     * Ce champ doit contenir la première partie d'un UUID
     */
    @Transient
    private String partialId ;

    public String getPartialId()
    {
        if (partialId == null) {
            this.partialId = this.id.split("-")[0] ;
        }

        return partialId ;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShrinkedURL that = (ShrinkedURL) o;

        return Objects.equals(sourceUrl, that.sourceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.sourceUrl) ;
    }

    @PrePersist
    public void beforeInsertion()
    {
        this.id = UUID.randomUUID().toString() ;
        this.addedAt = LocalDateTime.now() ;
    }
}
