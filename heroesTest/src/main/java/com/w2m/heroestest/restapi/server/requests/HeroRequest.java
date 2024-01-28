package com.w2m.heroestest.restapi.server.requests;

import com.w2m.heroestest.model.enums.SuperPower;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

