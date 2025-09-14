package ma.enset.iibdcc.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.iibdcc.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

/*
   ➡ Une table par entité, reliée par des jointures.
   Exemple : table PERSON (id, name, type), table STUDENT (id, major), table TEACHER (id, subject).
   ✔️ Avantages : structure plus propre, pas de colonnes NULL, normalisation.
   ❌ Inconvénients : requêtes plus lentes (jointures nécessaires).
   ✅ À utiliser quand : la hiérarchie est large/complexe et que tu veux éviter colonnes NULL.
@Inheritance(strategy = InheritanceType.JOINED)
 */

/*
 ➡ Chaque classe concrète a sa propre table indépendante (pas de table pour la classe parent).
 Exemple : table STUDENT (id, name, major), table TEACHER (id, name, subject).
 ✔️ Avantages : tables simples, pas de colonnes inutiles.
 ❌ Inconvénients : pas de table commune, requêtes polymorphiques (Person) = UNION coûteux.
 ✅ À utiliser quand : tu n’as presque jamais besoin de requêtes polymorphiques sur la classe parent.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
*/


@Entity
/**
 * ➡ Toutes les classes de l’héritage sont stockées dans UNE SEULE table.
 *Exemple : une seule table PERSON avec colonnes : id, name, major, subject, type
 *✔️ Avantages : meilleure performance (moins de jointures).
 *❌ Inconvénients : des colonnes NULL (champs spécifiques aux sous-classes).
 * ✅ À utiliser quand : la hiérarchie n’est pas trop complexe et que la perf prime.
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "TYPE",length = 4)

@Data @AllArgsConstructor @NoArgsConstructor
public abstract class  BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)//FetchType.EAGER
    private List<AccountOperation> accountOperations;
}
