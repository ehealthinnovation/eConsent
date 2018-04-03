package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api;

import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Questionnaire;

public interface IInitPatient {
    Patient postPatient(String name);
    Patient createPatient (String name);
}
