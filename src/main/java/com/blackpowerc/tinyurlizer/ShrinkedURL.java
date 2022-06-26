package com.blackpowerc.tinyurlizer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entité représentant une URL raccourcie.
 *
 * @author jordy jordy.fatigba@theopentrade.com
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "shrinked_url")
public class ShrinkedURL
{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id ;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt ;

    @URL
    @NotBlank
    @Column(name = "source_url", nullable = false)
    private String sourceUrl ;

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
    public void updateAddetAt() {
        this.addedAt = LocalDateTime.now() ;
    }
}
