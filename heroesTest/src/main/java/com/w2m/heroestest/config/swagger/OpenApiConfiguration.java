package com.w2m.heroestest.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author jruizh
 */
@OpenAPIDefinition(info = @Info(title = "Superheroes SpringBoot 3 API", version = "1.0", description = "This is an amazing API to define and classify superheroes", termsOfService = "http://swagger.io/terms/", license = @License(name = "Apache 2.0 Licence for super heroes", url = "http://springdoc.org/"))
        // , security = {@SecurityRequirement(name = "bearerAuth") }
)
//@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {
}
