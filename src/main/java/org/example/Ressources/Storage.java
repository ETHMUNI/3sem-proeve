package org.example.Ressources;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "timestamp", nullable = false)
    private LocalDate updatedTimeStamp;  // Changed to LocalDate

    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    @Column(name = "shelf_number", nullable = false)
    private int shelfNumber;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // Changed to LAZY loading
    private Set<HealthProduct> healthProducts = new HashSet<>();

    public Storage(LocalDate updatedTimeStamp, int totalAmount, int shelfNumber) {
        this.updatedTimeStamp = updatedTimeStamp;
        this.totalAmount = totalAmount;
        this.shelfNumber = shelfNumber;
    }
}
