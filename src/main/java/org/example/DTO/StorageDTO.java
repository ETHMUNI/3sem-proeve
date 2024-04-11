package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageDTO {
    private int id;
    private LocalDate updatedTimeStamp;
    private int totalAmount;
    private int shelfNumber;
    private Set<HealthProductDTO> healthProducts = new HashSet<>();
}
