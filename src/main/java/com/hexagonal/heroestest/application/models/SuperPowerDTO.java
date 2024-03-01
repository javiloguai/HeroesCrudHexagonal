package com.hexagonal.heroestest.application.models;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jruizh
 */
@Data
@Builder
public class SuperPowerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long superheroId;

    private SuperPower superPower;

}
