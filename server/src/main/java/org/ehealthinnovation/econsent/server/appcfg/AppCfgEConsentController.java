package org.ehealthinnovation.econsent.server.appcfg;

import org.ehealthinnovation.econsent.server.jsonsvc.controller.InitializerController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for the eConsent controllers
 */
@Configuration
public class AppCfgEConsentController {
    @Bean
    public InitializerController initController() {
        return new InitializerController();
    }
}
