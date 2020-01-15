import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './lawyer.reducer';
import { ILawyer } from 'app/shared/model/lawyer.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILawyerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILawyerUpdateState {
  isNew: boolean;
}

export class LawyerUpdate extends React.Component<ILawyerUpdateProps, ILawyerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { lawyerEntity } = this.props;
      const entity = {
        ...lawyerEntity,
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
    this.props.history.push('/lawyer');
  };

  render() {
    const { lawyerEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="mylawyerarsenohaApp.lawyer.home.createOrEditLabel">Create or edit a Lawyer</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : lawyerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="lawyer-id">ID</Label>
                    <AvInput id="lawyer-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="lawyerIdLabel" for="lawyer-lawyerId">
                    Lawyer Id
                  </Label>
                  <AvField id="lawyer-lawyerId" type="text" name="lawyerId" />
                </AvGroup>
                <AvGroup>
                  <Label id="lawyerFullNameLabel" for="lawyer-lawyerFullName">
                    Lawyer Full Name
                  </Label>
                  <AvField id="lawyer-lawyerFullName" type="text" name="lawyerFullName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lawyerPhoneNumberLabel" for="lawyer-lawyerPhoneNumber">
                    Lawyer Phone Number
                  </Label>
                  <AvField
                    id="lawyer-lawyerPhoneNumber"
                    type="text"
                    name="lawyerPhoneNumber"
                    validate={{
                      pattern: { value: '[0-9\\-\\+]+', errorMessage: "This field should follow pattern for '[0-9\\-\\+]+'." }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/lawyer" replace color="info">
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
  lawyerEntity: storeState.lawyer.entity,
  loading: storeState.lawyer.loading,
  updating: storeState.lawyer.updating,
  updateSuccess: storeState.lawyer.updateSuccess
});

const mapDispatchToProps = {
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
)(LawyerUpdate);
