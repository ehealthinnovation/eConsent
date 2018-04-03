package org.ehealthinnovation.econsent.server.jsonsvc.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.ParserOptions;
import ca.uhn.fhir.jpa.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.dao.SearchParameterMap;
import ca.uhn.fhir.rest.api.server.IBundleProvider;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitConsent;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitQuestionnaire;
import org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api.IInitQuestionnaireResponse;
import org.hl7.fhir.dstu3.model.Consent;
import org.hl7.fhir.dstu3.model.QuestionnaireResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController("InitializerController")
@RequestMapping(value = "/api/init", produces = {MediaType.APPLICATION_JSON_VALUE})
@Api(value = "App/Test Data Initialization")
@SwaggerDefinition(produces = {MediaType.APPLICATION_JSON_VALUE})
public class InitializerController {
    @Autowired
    IInitConsent myInitConsent;


    @PostConstruct
    public void initialize() {
        myInitConsent.createDefaultConsents();
    }

    @RequestMapping(value = "/$renew_test_data", method = {RequestMethod.GET}, produces = {MediaType.TEXT_PLAIN_VALUE})
    @Transactional
    @Timed
    @ExceptionMetered
    public ResponseEntity<String> initTestData() throws Exception {
        initialize();

        return new ResponseEntity<>("Completed", HttpStatus.OK);
    }
}

