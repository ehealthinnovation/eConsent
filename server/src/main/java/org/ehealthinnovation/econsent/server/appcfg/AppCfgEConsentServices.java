package org.ehealthinnovation.econsent.server.appcfg;

import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.*;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for the eConsent services
 */
@Configuration
public class AppCfgEConsentServices {

    @Bean
    public IInitConsent initConsent() {
        return new InitConsents();
    }

    @Bean
    public IInitPatient initPatient() {
        return new InitPatients();
    }

    @Bean
    public IInitRelatedPersons initRelatedPersons() {
        return new InitRelatedPersons();
    }

    @Bean
    public IInitProvenance initProvenance() {
        return new InitProvenance();
    }

    @Bean
    public IInitQuestionnaire initQuestionnaire() {
        return new InitQuestionnaireImpl();
    }

    @Bean
    public IInitQuestionnaireResponse initQuestionnaireResponse() {
        return new InitQuestionnaireResponseImpl();
    }
}
