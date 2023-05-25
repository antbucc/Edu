import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SetOfFragment from './set-of-fragment';
import SetOfFragmentDetail from './set-of-fragment-detail';
import SetOfFragmentUpdate from './set-of-fragment-update';
import SetOfFragmentDeleteDialog from './set-of-fragment-delete-dialog';

const SetOfFragmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SetOfFragment />} />
    <Route path="new" element={<SetOfFragmentUpdate />} />
    <Route path=":id">
      <Route index element={<SetOfFragmentDetail />} />
      <Route path="edit" element={<SetOfFragmentUpdate />} />
      <Route path="delete" element={<SetOfFragmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SetOfFragmentRoutes;
