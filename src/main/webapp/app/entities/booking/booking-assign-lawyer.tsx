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
import { getEntity,
         assignLawyer,
         reset } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookingAssignLawyerProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBookingAssignLawyerState {
  lawyerIdId: string;
  lawyers: ILawyer[];
}

export class BookingAssignLawyer extends React.Component<IBookingAssignLawyerProps, IBookingAssignLawyerState> {
  constructor(props) {
    super(props);
    this.state = {
      lawyers: [],
      lawyerIdId: '0'
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess &&
        nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
    this.props.getLawyers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bookingEntity } = this.props;
      const entity = {
        ...bookingEntity,
        ...values
      };

      this.props.assignLawyer(entity);

    }
  };

  handleClose = () => {
    event.stopPropagation();
    this.props.getEntity(this.props.bookingEntity.id);
    this.props.history.push(`/user/booking/${this.props.bookingEntity.id}`);
  };

  render() {
    const { bookingEntity, lawyers, loading, updating } = this.props;

    bookingEntity.bookingId = bookingEntity.id;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="mylawyerarsenohaApp.booking.home.assignLawyerLabel">Assign Lawyer to {bookingEntity.bookingName}</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={bookingEntity} onSubmit={this.saveEntity}>
                <AvGroup>
                  <AvField id="booking-bookingId" type="text" name="bookingId" readOnly hidden/>
                </AvGroup>
                <AvGroup>
                  <Label for="booking-lawyerId">Lawyer</Label>
                  <AvInput id="booking-lawyerId" type="select" className="form-control" name="lawyerId">
                    <option value="" key="0" />
                    {lawyers
                      ? lawyers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.lawyerFullName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="user/booking/" replace color="info">
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
  assignLawyer,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BookingAssignLawyer);
