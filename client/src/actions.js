import FHIREndpoints from './Services/FHIREndpoints'
import HTTPService from './Services/HTTPService'
export const REQUEST_PATIENT_CONSENTS = 'REQUEST_PATIENT_CONSENTS'
export const RECEIVE_PATIENT_CONSENTS = 'RECEIVE_PATIENT_CONSENTS'
export const REQUEST_PATIENTS = 'REQUEST_PATIENTS'
export const RECEIVE_PATIENTS = 'RECEIVE_PATIENTS'
export const SELECT_PATIENT = 'SELECT_PATIENT'
export const PATIENTS_SUCCESS = 'PATIENTS_SUCCESS'
export const PATIENTS_ERROR = 'PATIENTS_ERROR'
export const REQUEST_QUESTIONNAIRE = 'REQUEST_QUESTIONNAIRE'
export const RECEIVE_QUESTIONNAIRE = 'RECEIVE_QUESTIONNAIRE'
export const INVALIDATE_QUESTIONNAIRE = 'INVALIDATE_QUESTIONNAIRE'
export const INVALIDATE_PATIENTS = 'INVALIDATE_PATIENTS'
export const REQUEST_VALUE_SET = 'REQUEST_VALUE_SET'
export const RECEIVE_VALUE_SET = 'RECEIVE_VALUE_SET'

export const selectPatient = (patientId) => {
  return {
    type: SELECT_PATIENT,
    patientId
  }
}

export const requestValueSet = (valueSetReference) => {
  return {
    type: REQUEST_VALUE_SET,
    valueSetReference
  }
}

export const RECEIVEValueSet = (valueSet, valueSetReference) => {
  return {
    type: RECEIVE_VALUE_SET,
    valueSetReference,
    valueSet,
  }
}

export const requestPatients = () => {
  return {
    type: REQUEST_PATIENTS,
  }
}

export const receivePatients = (patients) => {
  return {
    type: RECEIVE_PATIENTS,
    // Return just the array of patient resources.
    patients: patients.entry.map(entry => entry.resource)
  }
}

export const requestQuestionnaire = (questionnaireId) => {
  return {
    type: REQUEST_QUESTIONNAIRE,
    questionnaireId
  }
}

export const receieveQuestionnaire = (questionnaire) => {
  return {
    type: RECEIVE_QUESTIONNAIRE,
    questionnaire
  }
}

export const requestPatientConsents = patientId => {
  return {
    type: REQUEST_PATIENT_CONSENTS,
    patientId,
  }
}

export const receivePatientConsents = (patientConsents) => {
  return {
    type: RECEIVE_PATIENT_CONSENTS,
    patientConsents,
  }
}

const fetchPatientConsents = (patientId) => {
  return async dispatch => {
    dispatch(requestPatientConsents)
    let res = await HTTPService.get(FHIREndpoints.patientConsents(patientId)).catch(e => { throw e })
    dispatch(receivePatientConsents(res))
  }
}

const shouldFetchPatientConsents = (state, patientId) => {
  if (state.patientConsentForms.length === 0) {
    return true
  } else if (state.isFetchingPatientConsentForms) {
    return false
  }
}

export const fetchPatientConsentsIfNeeded = (patientId) => {
  return (dispatch, getState) => {
    if (shouldFetchPatientConsents(getState(), patientId)) {
      return dispatch(fetchPatientConsents(patientId))
    } else {
      return Promise.resolve()
    }
  }
}

const fetchValueSet = (valueSetReference) => {
  return async dispatch => {
    dispatch(requestValueSet(valueSetReference))
    let res = await HTTPService.get(FHIREndpoints.valueSet(valueSetReference)).catch(e => { throw e })
    dispatch(RECEIVEValueSet(res, valueSetReference))
  }
}

const shouldFetchValueSet = (state, valueSetReference) => {
  if (!state[valueSetReference]) {
    return true
  }
}

export const fetchValueSetIfNeeded = (valueSetReference) => {
  return (dispatch, getState) => {
    if (shouldFetchValueSet(getState(), valueSetReference)) {
      return dispatch(fetchValueSet(valueSetReference))
    } else {
      return Promise.resolve()
    }
  }
}

const fetchQuestionnaire = (questionnaireId) => {
  return async dispatch => {
    dispatch(requestQuestionnaire(questionnaireId))
    let res = await HTTPService.get(FHIREndpoints.questionnaire(questionnaireId)).catch(e => { throw e })
    dispatch(receieveQuestionnaire(res))
  }
}

const fetchPatients = () => {
  return async dispatch => {
    dispatch(requestPatients)
    // Async await without a try catch block!
    // https://blog.grossman.io/how-to-write-async-await-without-try-catch-blocks-in-javascript/ (In comments by dominic108)
    let res = await HTTPService.get(FHIREndpoints.patients).catch(e => { throw e })
    dispatch(receivePatients(res))
  }
}

const shouldFetchPatients = (state) => {
  const patients = state.patients
  if (patients.length === 0) {
    return true
  } else if (patients.isFetching) {
    return false
  }
}

const shouldFetchQuestionnaire = (state, questionnaireId) => {
  if (state.questionnaire[questionnaireId]) {
    return false
  } else {
    return true
  }
}

export const fetchPatientsIfNeeded = () => {
  return (dispatch, getState) => {
    if (shouldFetchPatients(getState())) {
      return dispatch(fetchPatients())
    } else {
      return Promise.resolve()
    }
  }
}

export const fetchQuestionnaireIfNeeded = (questionnaireId) => {
  return (dispatch, getState) => {
    if (shouldFetchQuestionnaire(getState(), questionnaireId)) {
      return dispatch(fetchQuestionnaire(questionnaireId))
    } else {
      return Promise.resolve()
    }
  }
}
