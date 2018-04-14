import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import {
  fetchPatientConsentsIfNeeded,
} from '../../actions'
import Loading from '../../components/Loading'
import PatientConsentsTable from './components/PatientConsentsTable'
const propTypes = {
  history: PropTypes.object,
  match: PropTypes.object,
  dispatch: PropTypes.func.isRequired,
  patientConsentForms: PropTypes.array,
  isFetchingPatientConsentForms: PropTypes.bool,
  patientConsentFormsValid: PropTypes.bool,
}

class MyPatients extends React.Component {
  searchParamsForPatientId = (search) => {
    let patientId
    search.split('?')[1].split('&').forEach(param => {
      const params = param.split('=')
      if (params[0] === 'patientId') {
        patientId = params[1]
      }
    })
    return patientId
  }

  componentDidMount() {
    const { dispatch, location } = this.props
    const patientId = this.searchParamsForPatientId(location.search)
    this.props.dispatch(fetchPatientConsentsIfNeeded(patientId))
  }

  render() {
    const { patientConsentForms, isFetchingPatientConsentForms, patientConsentFormsValid } = this.props
    return (
      <div>
        {!patientConsentFormsValid && <Loading />}
        {(isFetchingPatientConsentForms && patientConsentForms.length === 0) && <Loading />}
        {!isFetchingPatientConsentForms && patientConsentForms.length === 0 && <h1>No patients.</h1>}
        {patientConsentForms.length > 0 && <PatientConsentsTable patientConsentForms={patientConsentForms} />}
      </div>
    )
  }
}

MyPatients.propTypes = propTypes

const mapStateToProps = (state) => {
  const { patientConsentForms, isFetchingPatientConsentForms, patientConsentFormsValid } = state
  return {
    patientConsentForms,
    isFetchingPatientConsentForms,
    patientConsentFormsValid,
  }
}

export default connect(mapStateToProps)(MyPatients)
