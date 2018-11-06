import React, { Component } from 'react'
import { Table, Panel, Glyphicon } from 'react-bootstrap'

import PaginationBar from '../PaginationBar/PaginationBar'

export default class BikeList extends Component {
  state = {
    bikes: [],
    pagination: {},
    loading: true
  }

  fetchBikes = ({ size = 10, number = 0 }) => {
    console.log(`/bike?page=${number}&size=${size}`)
    this.setState({ loading: true })
    fetch(`/bike?page=${number}&size=${size}`)
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
            },
            loading: false
          })
        }
      )
      .catch(error => console.error(error))
  }

  componentDidMount() {
    this.fetchBikes(this.state.pagination)
  }

  render() {
    const { bikes, pagination, loading } = this.state
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
              {loading == false ? (
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
        <Panel.Footer>
          <PaginationBar {...pagination} onClick={this.fetchBikes} />
        </Panel.Footer>
      </Panel>
    )
  }
}
