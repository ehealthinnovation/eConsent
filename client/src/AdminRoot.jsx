import React from 'react'
import { Link, Switch, Route, withRouter } from 'react-router-dom'

import MyPatients from './views/MyPatients'
import MyConsentForms from './views/MyConsentForms'
import GiveConsent from './views/GiveConsent'

import _ from 'lodash'

class Admin extends React.Component {
  state = {
    showPatientLogin: false,
    patientId: ''
  }

  onPatientClick = () => {
    this.setState({ showPatientLogin: !this.state.showPatientLogin })
  }

  onPatientLogin = (ev) => {
    ev.preventDefault()
    this.props.history.push('/admin/patients/?patientId='+this.state.patientId)
  }

  onPatientIdInput = (ev) => {
    this.setState({ patientId: ev.target.value })
  }

  render() {
    const { match, location } = this.props
    return (
      <div>
        <Link to="/">eConsent Home</Link>
        {_.startsWith(location.pathname, '/admin/consent') && <span>/<Link to="/admin/consent">Consent Admin</Link></span>}

        {match.isExact &&
          <div className="level" style={{
            display: 'flex',
            justifyContent: 'center',
            height: '100vh'
          }}>
            <div className="level-item" />
              <Link to="/admin/consent"><button className="button level-item" style={{ height: '200px', width: '300px'}}>Clinician</button></Link>
            <div className="level-item" />
              <div className="level-item">
                <button className="button" onClick={this.onPatientClick} style={{ height: '200px', width: '300px'}}>Patient Consents</button>
                {
                  this.state.showPatientLogin &&
                  <form onSubmit={this.onPatientLogin}>
                    Patient ID:
                    <input name="patientId" onChange={this.onPatientIdInput}/>
                    <button type="submit">
                      Login
                    </button>
                  </form>
                }
              </div>
            <div className="level-item" />
            <Link to="/admin/give-consent"><button className="button level-item" style={{ height: '200px', width: '200px'}}>Give Consent</button></Link>
            <div className="level-item" />
          </div>
        }

        <Switch>
          <Route path="/admin/consent" component={MyConsentForms} />
          <Route path="/admin/patients" component={MyPatients} />
          <Route path="/admin/give-consent" component={GiveConsent} />
        </Switch>
      </div>
    )
  }
}
export default withRouter(Admin)
