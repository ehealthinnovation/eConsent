export const questionnaireIds = {
  template1: 'template1',
  sickKids: 'sickKids'
}

const FHIREndpoints = {
  valueSet: (valueSetReference) => `fhir/${valueSetReference}`,
  patient: 'fhir/Patient',
  patientConsents: id => `fhir/Consent/${id}`,
  questionnaire: (questionnaireId) => `fhir/Questionnaire/${questionnaireId}`,
  QRs: (qrId) => `fhir/QuestionnaireResponse/${qrId}`
}

export default FHIREndpoints
