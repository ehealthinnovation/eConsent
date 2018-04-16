import {
  SELECT_PATIENT,
  REQUEST_PATIENTS,
  RECEIVE_PATIENTS,
  RECEIVE_QUESTIONNAIRE,
  REQUEST_QUESTIONNAIRE,
  REQUEST_VALUE_SET,
  RECEIVE_VALUE_SET,
  REQUEST_PATIENT_CONSENTS,
  RECEIVE_PATIENT_CONSENTS,
} from './actions'
// import { combineReducers } from 'redux'

const defaultState = {
  patients: [],
  clinicianConsentForms: [{ name: 'ICC', status: 'in-progress'}, { name: 'band', status: 'complete'}],
  patientConsentForms: [],
  isFetchingPatientConsentForms: false,
  patientConsentFormsValid: false,
  isFetchingPatients: false,
  isFetchingQuestionnaire: false,
  isFetchingValueSet: false,
  patientsValid: false,
  valueSet: {},
  questionnaireValid: false,
  questionnaire: {item: []}
}

const eConsentApp = (state = defaultState, action) => {
  const actionType = action.type
  switch (actionType) {
    case REQUEST_VALUE_SET:
      return {
        ...state,
        isFetchingValueSet: true
      }
    case RECEIVE_VALUE_SET:
      return {
        ...state,
        isFetchingValueSet: false,
        valueSet: {
          ...state.valueSet,
          [action.valueSetReference]: action.valueSet.compose.include[0].concept.map(concept => concept.display)
        }
      }
    case REQUEST_QUESTIONNAIRE:
      return {
        ...state,
        isFetchingQuestionnaire: true,
      }
    case RECEIVE_QUESTIONNAIRE:
      return {
        ...state,
        isFetchingQuestionnaire: false,
        questionnaireValid: true,
        questionnaire: action.questionnaire
      }

    case SELECT_PATIENT:
      console.log('You selected patient', action.patientId)
      break;

    case REQUEST_PATIENT_CONSENTS:
      return {
        ...state,
        isFetchingPatientConsentForms: true,
      }

    case RECEIVE_PATIENT_CONSENTS:
      return {
        ...state,
        isFetchingPatientConsentForms: false,
        patientConsentFormsValid: true,
        patientConsentForms: action.patientConsents
      }

    case REQUEST_PATIENTS:
      return { ...state, isFetchingPatients: true }

    case RECEIVE_PATIENTS:
      return {
        ...state,
        isFetchingPatients: false,
        patientsValid: true,
        patients: action.patients.map(patient => {
          const firstName = (patient.name[0].given && patient.name[0].given.join(' '))
          const lastName = patient.name[0].family || 'no family name'
          return {
            name: firstName + " " + lastName,
            id: patient.id
          }
        })
      }

      default:
        return state
  }
}

export default eConsentApp
