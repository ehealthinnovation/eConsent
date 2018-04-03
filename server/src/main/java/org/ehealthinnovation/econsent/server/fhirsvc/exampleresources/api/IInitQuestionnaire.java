package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api;

import org.hl7.fhir.dstu3.model.Questionnaire;

public interface IInitQuestionnaire {
    Questionnaire postQuestionnaire();
    Questionnaire createQuestionnaire();
}
