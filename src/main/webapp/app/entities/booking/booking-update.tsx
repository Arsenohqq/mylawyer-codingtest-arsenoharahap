import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILawyer } from 'app/shared/model/lawyer.model';
import { getEntities as getLawyers } from 'app/entities/lawyer/lawyer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBookingUpdateState {
  isNew: boolean;
  lawyerIdId: string;
}

export class BookingUpdate extends React.Component<IBookingUpdateProps, IBookingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      lawyerIdId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getLawyers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bookingEntity } = this.props;
      const entity = {
        ...bookingEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/booking');
  };

  render() {
    const { bookingEntity, lawyers, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="mylawyerarsenohaApp.booking.home.createOrEditLabel">Create or edit a Booking</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bookingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="booking-id">ID</Label>
                    <AvInput id="booking-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="bookingIdLabel" for="booking-bookingId">
                    Booking Id
                  </Label>
                  <AvField id="booking-bookingId" type="text" name="bookingId" />
                </AvGroup>
                <AvGroup>
                  <Label id="bookingNameLabel" for="booking-bookingName">
                    Booking Name
                  </Label>
                  <AvField id="booking-bookingName" type="text" name="bookingName" />
                </AvGroup>
                <AvGroup>
                  <Label id="paymentApprovedLabel" check>
                    <AvInput id="booking-paymentApproved" type="checkbox" className="form-control" name="paymentApproved" />
                    Payment Approved
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="booking-lawyerId">Lawyer Id</Label>
                  <AvInput id="booking-lawyerId" type="select" className="form-control" name="lawyerIdId">
                    <option value="" key="0" />
                    {lawyers
                      ? lawyers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/booking" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  lawyers: storeState.lawyer.entities,
  bookingEntity: storeState.booking.entity,
  loading: storeState.booking.loading,
  updating: storeState.booking.updating,
  updateSuccess: storeState.booking.updateSuccess
});

const mapDispatchToProps = {
  getLawyers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookingUpdate);
