package com.hexagonal.heroestest.domain.models;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuperPowerDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long superheroId;

    private SuperPower superPower;

}
