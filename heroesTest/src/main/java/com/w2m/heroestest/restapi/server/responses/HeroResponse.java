package com.w2m.heroestest.restapi.server.responses;

import com.w2m.heroestest.model.enums.SuperPower;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HeroResponse {

    private String id;

    private String name;

    private String description;

    private List<SuperPower> superPower;

}

