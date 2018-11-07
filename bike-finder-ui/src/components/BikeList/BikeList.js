import React, { Component } from 'react'
import { Table, Panel } from 'react-bootstrap'

import PaginationBar from '../PaginationBar/PaginationBar'
import { Loading } from '../common'

export default class BikeList extends Component {
  state = {
    bikes: [],
    pagination: {},
    loading: true,
    error: undefined
  }

  fetchBikes = ({ size = 10, number = 0 }) => {
    console.log(`/bike?page=${number}&size=${size}`)
    this.setState({ loading: true, error: undefined })
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
      .catch(error => {
        console.error(error)
        this.setState({
          bikes: [],
          loading: false,
          error: 'Somenthing wrong happened'
        })
      })
  }

  componentDidMount() {
    this.fetchBikes(this.state.pagination)
  }

  render() {
    const { bikes = [], pagination, loading, error } = this.state
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
              {!loading ? (
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
                    <Loading />
                  </td>
                </tr>
              )}
            </tbody>
          </Table>
        </Panel.Body>
        <Panel.Footer>
          {!loading || bikes.length ? (
            <PaginationBar {...pagination} onClick={this.fetchBikes} />
          ) : (
            <div />
          )}
        </Panel.Footer>
      </Panel>
    )
  }
}
