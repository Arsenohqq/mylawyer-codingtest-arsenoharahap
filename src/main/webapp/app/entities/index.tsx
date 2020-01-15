import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lawyer from './lawyer';
import Booking from './booking';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/lawyer/getLawyerList`} component={Lawyer} />
      <ErrorBoundaryRoute path={`${match.url}/lawyer`} component={Lawyer} />
      <ErrorBoundaryRoute path={`${match.url}/booking/getBookingList`} component={Booking} />
      <ErrorBoundaryRoute path={`${match.url}/booking`} component={Booking} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
