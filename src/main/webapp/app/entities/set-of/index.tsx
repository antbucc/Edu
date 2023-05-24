import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SetOf from './set-of';
import SetOfDetail from './set-of-detail';
import SetOfUpdate from './set-of-update';
import SetOfDeleteDialog from './set-of-delete-dialog';

const SetOfRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SetOf />} />
    <Route path="new" element={<SetOfUpdate />} />
    <Route path=":id">
      <Route index element={<SetOfDetail />} />
      <Route path="edit" element={<SetOfUpdate />} />
      <Route path="delete" element={<SetOfDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SetOfRoutes;
