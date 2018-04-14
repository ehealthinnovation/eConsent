import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { Link, Switch, Route } from 'react-router-dom'

import NewConsentForm from './scenes/NewConsentForm'

const propTypes = {
  clinicianConsentForms: PropTypes.array.isRequired,
}

class MyConsentForms extends React.Component {
  render() {
    const { clinicianConsentForms, match } = this.props
    return (
      <div>
        { match.isExact && (
          <div>
            <Link to='/admin/consent/new'>
              <button className="button">New Consent Form</button>
            </Link>
            <table className="table is-striped is-bordered is-fullwidth">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Status</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {clinicianConsentForms.map(consent => {
                  return <tr>
                    <td>{consent.name}</td>
                    <td>{consent.status}</td>
                    <td>Edit</td>
                  </tr>
                })}
              </tbody>
            </table>
          </div>
        )}
        <Switch>
          <Route path='/admin/consent/new' component={NewConsentForm}/>
        </Switch>
      </div>
    )
  }
}

MyConsentForms.propTypes = propTypes

const mapStateToProps = (state) => {
  const { clinicianConsentForms } = state
  return {
    clinicianConsentForms,
  }
}

export default connect(mapStateToProps)(MyConsentForms)
