import React from 'react'
import { fetchValueSetIfNeeded } from '../../../../../actions'
// import PropTypes from 'prop-types'
import { connect } from 'react-redux'

class LearnMoreChoices extends React.Component {
  componentDidMount() {
    this.props.dispatch(fetchValueSetIfNeeded(this.props.reference))
  }

  render() {
    return (
      <div>
      {!this.props.isFetchingValueSet &&
        this.props.valueSet[this.props.reference] &&
        this.props.valueSet[this.props.reference].map(choice => {
        return <div>
          <label className="checkbox"/>
            <div>
              <input type="checkbox"/>
              {choice}
            </div>
        </div>
      })}
      </div>
    )
  }
}

const mapStateToProps = (state) => {
  const { isFetchingValueSet, valueSet } = state
  return {
    valueSet,
    isFetchingValueSet,
  }
}

export default connect(mapStateToProps)(LearnMoreChoices)
