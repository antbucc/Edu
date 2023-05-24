import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Set from './set';
import SetDetail from './set-detail';
import SetUpdate from './set-update';
import SetDeleteDialog from './set-delete-dialog';

const SetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Set />} />
    <Route path="new" element={<SetUpdate />} />
    <Route path=":id">
      <Route index element={<SetDetail />} />
      <Route path="edit" element={<SetUpdate />} />
      <Route path="delete" element={<SetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SetRoutes;
