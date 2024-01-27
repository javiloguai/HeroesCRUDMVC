package com.w2m.heroestest.utils;



import com.w2m.heroestest.model.enums.Role;
import com.w2m.heroestest.restapi.persistence.entities.User;


/**
 * The Class MockSecurity.
 */
public final class MockSecurity {

    /**
     * Instantiates a new mock security.
     */
    private MockSecurity() {
    }

    /**
     * Gets the user.
     * By default
     * Id = 1
     * userName=fooname
     * language=fooLang
     *
     * @return the user
     */
    public static User getUser(Role role) {



        return User.builder().id(1).email("fooname@fooname.com").firstname("fooname").role(role).password("fooname").build();
    }


//    /**
//     * Mock autentication.
//     *
//     * @param user the user
//     * @return the authentication
//     */
//    public static Authentication mockAuthentication(final User user) {
//        final Authentication authentication = Mockito.mock(Authentication.class);
//        Mockito.when(authentication.getPrincipal()).thenReturn(user);
//        return authentication;
//    }

//    /**
//     * Sets the mock user in test.
//     *
//     * @param user the new mock user in test
//     * @return the security context
//     */
//    public static SecurityContext setMockUserInTest(final User user) {
//        final SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        final Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
//                user.getAuthorities());
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        return securityContext;
//    }


    /**
     * Clear user from context.
     */
//    public static void clearUserFromContext() {
//        SecurityContextHolder.clearContext();
//    }


}
