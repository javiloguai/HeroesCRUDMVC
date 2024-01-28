package com.w2m.heroestest.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author jruizh
 */
@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class SuperHeroDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;

    private List<SuperPowerDomain> superPower;

}
