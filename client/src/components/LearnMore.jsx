import React from 'react'

class LearnMore extends React.Component {
  state = {
    showMore: false,
  }

  toggle = () => {
    this.setState({
      showMore: !this.state.showMore
    })
  }

  render () {
    const { title, summary, learnMore } = this.props
    return (
      <div className="box">
        <h3>{title}</h3>
        <h4><b>{summary}</b></h4>
        { !this.state.showMore && <p>{learnMore.substring(0,100)}...</p>}
        { !this.state.showMore && <button onClick={this.toggle} className="button">Show more...</button>}
        { this.state.showMore && <p>{learnMore}</p>}
        { this.state.showMore && <button onClick={this.toggle} className="button">Show less...</button>}
      </div>
    )
  }
}

export default LearnMore
