package org.example.Ressources;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime updatedTimeStamp;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private int shelfNumber;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    private Set<HealthProduct> healthProducts = new HashSet<>();

    public Storage(LocalDate updatedTimeStamp, int totalAmount, int shelfNumber) {
        this.updatedTimeStamp = updatedTimeStamp.atStartOfDay();
        this.totalAmount = totalAmount;
        this.shelfNumber = shelfNumber;
    }
}
