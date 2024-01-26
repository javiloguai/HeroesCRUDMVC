package com.w2m.heroestest.core.model.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jruizh
 */
@Data
@Builder
//@NoArgsConstructor
public class SuperHeroDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;

    private List<SuperPowerDomain> superPower;

}
