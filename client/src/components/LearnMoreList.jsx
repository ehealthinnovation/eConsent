import React from 'react'

class LearnMoreList extends React.Component {
  state = {
    showMore: false,
  }

  toggle = () => {
    this.setState({
      showMore: !this.state.showMore
    })
  }

  render () {
    const { title, list, learnMore } = this.props
    return (
      <div className="box">

        <h3>{title}</h3>
        <ul>
          {list.map(answer => {
            return <li key={answer.valueCoding.code}><b>{answer.valueCoding.display}</b></li>
          })}
        </ul>
        { !this.state.showMore && <p>{learnMore.substring(0,100)}...</p>}
        { !this.state.showMore && <button onClick={this.toggle} className="button">Show more...</button>}
        { this.state.showMore && <p>{learnMore}</p>}
        { this.state.showMore && <button onClick={this.toggle} className="button">Show less...</button>}
      </div>
    )
  }
}

export default LearnMoreList

