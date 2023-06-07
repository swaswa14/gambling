package ph.cdo.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ph.cdo.backend.entity.base.BaseEntity;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.TokenType;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EnableJpaAuditing
@Data
@Entity
@SequenceGenerator(name = "entity_sequence", sequenceName = "token_seq", allocationSize = 1)
public class Token extends BaseEntity {
    private static final long serialVersionUID = 1L;



    @Builder.Default
    @Column(updatable = false, nullable = false)
    private final String token = UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private TokenType tokenType;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

}
