import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Booking from './booking';
import BookingDetail from './booking-detail';
import BookingUpdate from './booking-update';
import BookingDeleteDialog from './booking-delete-dialog';
import BookingApprovePayment from './booking-approve-payment';
import BookingAssignLawyer from './booking-assign-lawyer';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookingDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/approvePayment`} component={BookingApprovePayment} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/assignLawyer`} component={BookingAssignLawyer} />
      <ErrorBoundaryRoute path={match.url} component={Booking} />
      <ErrorBoundaryRoute exact path={`${match.url}/getBookingList`} component={Booking} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BookingDeleteDialog} />
  </>
);

export default Routes;
