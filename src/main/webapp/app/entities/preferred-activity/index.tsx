import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredActivity from './preferred-activity';
import PreferredActivityDetail from './preferred-activity-detail';
import PreferredActivityUpdate from './preferred-activity-update';
import PreferredActivityDeleteDialog from './preferred-activity-delete-dialog';

const PreferredActivityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredActivity />} />
    <Route path="new" element={<PreferredActivityUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredActivityDetail />} />
      <Route path="edit" element={<PreferredActivityUpdate />} />
      <Route path="delete" element={<PreferredActivityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredActivityRoutes;
