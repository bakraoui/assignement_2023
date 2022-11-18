package ma.octo.assignement.domain.operation;

import lombok.*;
import ma.octo.assignement.domain.Compte;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal Montant;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecution;

    @Column
    private String nomPrenomEmetteur;

    @ManyToOne
    private Compte compteBeneficiaire;

    @Column(length = 200)
    private String motif;

}
