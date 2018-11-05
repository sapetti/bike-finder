import React, { Component } from 'react'
import './App.css'

import BikeList from './components/BikeList/BikeList'

class App extends Component {
  render() {
    return (
      <div className="App">
        <BikeList />
      </div>
    )
  }
}

export default App
