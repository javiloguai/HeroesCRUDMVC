package com.w2m.heroestest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jruizh
 */
@Data
@Builder
public class SuperHeroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;

    private List<SuperPowerDTO> superPower;

}
