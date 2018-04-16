import React from 'react'

class Dropdown extends React.Component {
  state = {
    showDropdown: false,
    selected: '',
  }

  componentDidMount() {
    console.log('Dropdown')
  }

  onClick =() => {
    this.setState({ showDropdown: true })
  }

  onBlur = () => {
    this.setState({ showDropdown: false })
  }

  onSelect = value => {
    console.log(value)
    this.props.onSelect(value)
    this.setState({ selected: value, showDropdown: false })
  }

  render() {
    return <div className={"dropdown " + (this.state.showDropdown && 'is-active ')}>
      <div className="dropdown-trigger">
        <button onClick={this.onClick} className="button" aria-haspopup="true" aria-controls="dropdown-menu">
          <span>{this.state.selected || this.props.placeholder}</span>
          <span className="icon is-small">
            <i className="fas fa-angle-down" aria-hidden="true"></i>
          </span>
        </button>
      </div>
      <div className="dropdown-menu" id="dropdown-menu" role="menu">
        <div className="dropdown-content">
          {this.props.dropdownOptions.map(option => {
            return (
              <a
                key={option}
                className={'dropdown-item ' + (this.state.selected === option && 'is-active')}
                onClick={() => this.onSelect(option)}
              >
                {option}
              </a>
            )
          })}
        </div>
      </div>
    </div>
  }
}

export default Dropdown
