/* eslint no-console: off */
import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BookingDetail extends React.Component<IBookingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  renderNextActionButton(bookingEntity) {
    const { match } =this.props;
    if(!bookingEntity.paymentApproved){
        return (
          <Button tag={Link} to={`${match.url}/approvePayment`} replace color="success">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Approve Payment</span>
          </Button>
        );
    } else if (bookingEntity.lawyerId === null){
      return (
        <Button tag={Link} to={`${match.url}/assignLawyer`} replace color="success">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Assign Lawyer</span>
        </Button>
      );
    } else return null;

  }

  render() {
    const { bookingEntity } = this.props;
    console.log('===detail booking===');
    console.log(bookingEntity);
    console.log('====================');
    return (
      <Row>
        <Col md="8">
          <h2>
            Booking [<b>{bookingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="bookingId">Booking Id</span>
            </dt>
            <dd>{bookingEntity.bookingId}</dd>
            <dt>
              <span id="bookingName">Booking Name</span>
            </dt>
            <dd>{bookingEntity.bookingName}</dd>
            <dt>
              <span id="paymentApproved">Payment Approved</span>
            </dt>
            <dd>{bookingEntity.paymentApproved ? 'true' : 'false'}</dd>
            <dt>Lawyer</dt>
            <dd>{bookingEntity.lawyerId ? bookingEntity.lawyerId.lawyerFullName : ''}</dd>
          </dl>
          <Button tag={Link} to="/booking" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
          &nbsp;
          {this.renderNextActionButton(bookingEntity)}
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookingDetail);
