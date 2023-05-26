import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FragmentSet from './fragment-set';
import FragmentSetDetail from './fragment-set-detail';
import FragmentSetUpdate from './fragment-set-update';
import FragmentSetDeleteDialog from './fragment-set-delete-dialog';

const FragmentSetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FragmentSet />} />
    <Route path="new" element={<FragmentSetUpdate />} />
    <Route path=":id">
      <Route index element={<FragmentSetDetail />} />
      <Route path="edit" element={<FragmentSetUpdate />} />
      <Route path="delete" element={<FragmentSetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FragmentSetRoutes;
