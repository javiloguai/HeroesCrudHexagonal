package com.hexagonal.heroestest.infraestructure.restapi.model.requests;

import com.hexagonal.heroestest.domain.enums.SuperPower;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private List<SuperPower> superPower;

}

