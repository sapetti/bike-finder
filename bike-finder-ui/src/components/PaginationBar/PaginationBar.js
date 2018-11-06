import React, { Component } from 'react'
import { Button, ButtonGroup, ButtonToolbar, Col, Badge } from 'react-bootstrap'

const DOTS = '...'

export default class PaginationBar extends Component {
  render() {
    const { totalPages, totalElements, size, number, first, last } = this.props
    let numbers = Array(totalPages)
      .fill(1)
      .map((x, y) => x + y)
    if (totalPages > 7) {
      if (number < 4) {
        numbers = numbers.slice(0, 5).concat(DOTS, totalPages)
      } else if (number > totalPages - 5) {
        numbers = [1].concat(DOTS, numbers.slice(totalPages - 5, totalPages))
      } else {
        numbers = [1].concat(
          DOTS,
          numbers.slice(number - 2, number + 3),
          DOTS,
          totalPages
        )
      }
    }

    return (
      <Col>
        <ButtonToolbar>
          <ButtonGroup>
            {numbers.map(
              (num, index) =>
                DOTS == num ? (
                  <Button
                    key={num + index}
                    bsStyle="link"
                    style={{ cursor: 'default' }}
                    disabled
                  >
                    {num}
                  </Button>
                ) : (
                  <Button
                    key={num + index}
                    bsStyle="link"
                    onClick={this.props.onClick.bind(null, { number: num - 1 })}
                  >
                    {num - 1 == number ? <Badge>{num}</Badge> : num}
                  </Button>
                )
            )}
          </ButtonGroup>
        </ButtonToolbar>
      </Col>
    )
  }
}
