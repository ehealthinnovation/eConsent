package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api;

import org.hl7.fhir.dstu3.model.QuestionnaireResponse;
import org.hl7.fhir.instance.model.api.IIdType;

import java.util.List;

public interface IInitQuestionnaireResponse {
    QuestionnaireResponse postQuestionnaireResponse(QuestionnaireResponse theQuestionnaireResponse);

    QuestionnaireResponse createQuestionnaireResponse(String theName, IIdType theBasedOn);

    List<QuestionnaireResponse> createQuestionnaireResponses();
}
