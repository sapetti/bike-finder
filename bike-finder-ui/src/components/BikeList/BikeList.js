import React, { Component } from 'react'
import { Table, Panel, Glyphicon } from 'react-bootstrap'

// import PaginationBar from '../pagination/PaginationBar'

export default class BikeList extends Component {
  state = {
    bikes: []
  }
  componentDidMount() {
    fetch('/bike')
      .then(response => response.json())
      .then(
        ({ content, totalPages, totalElements, size, number, first, last }) => {
          this.setState({
            bikes: content,
            pagination: {
              totalPages,
              totalElements,
              size,
              number,
              first,
              last
            }
          })
        }
      )
      .catch(error => console.error(error))
  }

  render() {
    const { bikes, pagination } = this.state
    return (
      <Panel>
        <Panel.Heading>
          <h1>Bike Finder</h1>
        </Panel.Heading>
        <Panel.Body>
          <Table responsive>
            <thead>
              <tr>
                <th>Maker</th>
                <th>Model</th>
                <th>Category</th>
                <th>Year</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              {bikes.length ? (
                bikes.map(({ id, name, maker, category, year, price }) => (
                  <tr key={id}>
                    <td>{maker.name}</td>
                    <td>{name}</td>
                    <td>{category}</td>
                    <td>{year}</td>
                    <td>{price}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5">
                    <Glyphicon
                      glyph="glyphicon glyphicon-refresh"
                      className="spin"
                    />
                  </td>
                </tr>
              )}
            </tbody>
          </Table>
        </Panel.Body>
        <Panel.Footer>{/* <PaginationBar {...pagination} /> */}</Panel.Footer>
      </Panel>
    )
  }
}
