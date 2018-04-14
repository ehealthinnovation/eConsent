class AuthService {
  isAuthenticated = () => {
    // TODO: Always return true for now, add following checks when auth is set up on the server.
      // If token doesnt exist, return false
      // If token is invalid, return false
    return true
  }
}

export default new AuthService()
