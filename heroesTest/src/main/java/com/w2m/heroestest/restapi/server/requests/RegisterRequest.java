package com.w2m.heroestest.restapi.server.requests;

import com.w2m.heroestest.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jruizh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    /*private String firstName;

    private String lastName;

    private String email;*/

    private Role role;
}
