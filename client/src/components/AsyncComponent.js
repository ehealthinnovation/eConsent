//Wrap components in this Async Component to allow for code splitting, only loading portions of the app as needed.
//https://serverless-stack.com/chapters/code-splitting-in-create-react-app.html
import React from "react";

export default function asyncComponent(importComponent) {
  class AsyncComponent extends React.Component {
    constructor(props) {
      super(props);

      this.state = {
        component: null
      };
    }

    async componentDidMount() {
      const { default: component } = await importComponent();

      this.setState({
        component: component
      });
    }

    render() {
      const Component = this.state.component;

      return Component ? <Component {...this.props} /> : null;
    }
  }

  return AsyncComponent;
}
