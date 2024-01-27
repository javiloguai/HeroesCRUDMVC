package com.w2m.heroestest.model.dto;

import com.w2m.heroestest.model.enums.SuperPower;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jruizh
 */
@Data
@Builder
//@NoArgsConstructor
public class SuperPowerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private Long superheroId;

    private SuperPower superPower;

}
