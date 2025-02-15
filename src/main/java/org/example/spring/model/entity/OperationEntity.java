package org.example.spring.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "operations")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "id_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "sum")
    private Long sum;

    @Column(name = "cur_code")
    private String currencyCode;
}
