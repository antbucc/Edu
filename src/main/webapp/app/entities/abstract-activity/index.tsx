import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AbstractActivity from './abstract-activity';
import AbstractActivityDetail from './abstract-activity-detail';
import AbstractActivityUpdate from './abstract-activity-update';
import AbstractActivityDeleteDialog from './abstract-activity-delete-dialog';

const AbstractActivityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AbstractActivity />} />
    <Route path="new" element={<AbstractActivityUpdate />} />
    <Route path=":id">
      <Route index element={<AbstractActivityDetail />} />
      <Route path="edit" element={<AbstractActivityUpdate />} />
      <Route path="delete" element={<AbstractActivityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AbstractActivityRoutes;
