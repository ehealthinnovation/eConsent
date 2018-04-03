package org.ehealthinnovation.econsent.server.fhirsvc.exampleresources.api;

import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.RelatedPerson;

public interface IInitRelatedPersons {
    RelatedPerson postRelatedPerson(RelatedPerson person);
    RelatedPerson createRelatedPerson(Patient theRelatedToPatient, Coding theRelationship, String theFamilyName);
}
