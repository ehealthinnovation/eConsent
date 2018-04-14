export const questionnaireIds = {
  template1: 'template1'
}

const FHIREndpoints = {
  valueSet: (valueSetReference) => `fhir/${valueSetReference}`,
  patient: 'fhir/Patient',
  questionnaire: (questionnaireId) => `fhir/Questionnaire/${questionnaireId}`,
  QRs: (qrId) => `fhir/QuestionnaireResponse/${qrId}`
}

export default FHIREndpoints
