import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './lawyer.reducer';
import { ILawyer } from 'app/shared/model/lawyer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILawyerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LawyerDetail extends React.Component<ILawyerDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { lawyerEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Lawyer [<b>{lawyerEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="lawyerId">Lawyer Id</span>
            </dt>
            <dd>{lawyerEntity.lawyerId}</dd>
            <dt>
              <span id="lawyerFullName">Lawyer Full Name</span>
            </dt>
            <dd>{lawyerEntity.lawyerFullName}</dd>
            <dt>
              <span id="lawyerPhoneNumber">Lawyer Phone Number</span>
            </dt>
            <dd>{lawyerEntity.lawyerPhoneNumber}</dd>
          </dl>
          <Button tag={Link} to="/lawyer" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/lawyer/${lawyerEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ lawyer }: IRootState) => ({
  lawyerEntity: lawyer.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LawyerDetail);
