package org.example.spring.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "currencies")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyEntity {
    @Id
    private Long id;
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
