package com.w2m.heroestest.restapi.server.responses;

import com.w2m.heroestest.model.enums.SuperPower;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
public class HeroResponse extends RepresentationModel<HeroResponse> {

    private String id;

    private String name;

    private String description;

    private List<SuperPower> superPower;

}

