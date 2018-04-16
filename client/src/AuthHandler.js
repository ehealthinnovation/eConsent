import React from 'react'
import { Redirect, Switch, Route } from 'react-router-dom'
import AuthService from './Services/AuthService'
import AdminRoot from './AdminRoot'

const AuthHandler = () => {
  return (
    <Switch>
      <Route path="/login" render={props => {
        //TODO: Redirect to admin page if the user is logged in already.
        return <h1>Im a login page.</h1>
      }} />
      <Route path="/" render={props => {
        if (!AuthService.isAuthenticated()) {
          return <Redirect to={{
            pathname: "/login",
            state: { from: props.location }
           }}/>
        }
        return <AdminRoot />
      }} />
    </Switch>
  )
}

export default AuthHandler
