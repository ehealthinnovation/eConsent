import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import { Provider } from 'react-redux'
import thunkMiddleware from 'redux-thunk'
import { createLogger } from 'redux-logger'
import { createStore, applyMiddleware } from 'redux'
import eConsentApp from './reducers'
import './index.css';
import './App.css';
import '../node_modules/bulma/css/bulma.css'
import registerServiceWorker from './registerServiceWorker';
import AsyncComponent from './components/AsyncComponent';

const loggerMiddleware = createLogger()

const store = createStore(
  eConsentApp,
  applyMiddleware(
    thunkMiddleware,
    loggerMiddleware,
  )
)

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter basename={process.env.REACT_APP_BASENAME || ''}>
      <Switch>
        <Route path="/econsentform/:id" component={AsyncComponent(() => import('./views/public/ConsentTemplate'))} />
        <Route path="/" component={AsyncComponent(() => import('./AuthHandler'))} />
      </Switch>
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);
registerServiceWorker();
