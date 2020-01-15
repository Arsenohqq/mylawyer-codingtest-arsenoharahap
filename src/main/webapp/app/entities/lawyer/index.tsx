import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lawyer from './lawyer';
import LawyerDetail from './lawyer-detail';
import LawyerUpdate from './lawyer-update';
import LawyerDeleteDialog from './lawyer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LawyerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LawyerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LawyerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Lawyer} />
      <ErrorBoundaryRoute path={`${match.url}/getLawyerList`} component={Lawyer} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LawyerDeleteDialog} />
  </>
);

export default Routes;
