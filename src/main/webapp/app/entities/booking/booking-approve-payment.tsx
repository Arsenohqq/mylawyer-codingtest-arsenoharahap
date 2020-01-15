/* eslint no-console: off */
import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity, approvePayment } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BookingApprovePayment extends React.Component<IBookingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmApprove = event => {
    console.log("===approve payment from dispatch===");
    this.props.approvePayment(this.props.bookingEntity.id);

    this.handleClose(event);
  };

  handleClose = event => {
    console.log("===handle close===");
    console.log(this.props.history);
    console.log("==================");
    event.stopPropagation();
    this.props.getEntity(this.props.bookingEntity.id);
    this.props.history.push(`/user/booking/${this.props.bookingEntity.id}`);

  };

  render() {
    const { bookingEntity } = this.props;
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
            <dt>Lawyer Id</dt>
            <dd>{bookingEntity.lawyerIdId ? bookingEntity.lawyerIdId : ''}</dd>
          </dl>
          <h6>
            Approve payment for this booking?
          </h6>
          <Button onClick={this.confirmApprove} replace color="success">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Approve</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity
});

const mapDispatchToProps = { getEntity, approvePayment };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookingApprovePayment);
