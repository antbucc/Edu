import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FragmentOrder from './fragment-order';
import FragmentOrderDetail from './fragment-order-detail';
import FragmentOrderUpdate from './fragment-order-update';
import FragmentOrderDeleteDialog from './fragment-order-delete-dialog';

const FragmentOrderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FragmentOrder />} />
    <Route path="new" element={<FragmentOrderUpdate />} />
    <Route path=":id">
      <Route index element={<FragmentOrderDetail />} />
      <Route path="edit" element={<FragmentOrderUpdate />} />
      <Route path="delete" element={<FragmentOrderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FragmentOrderRoutes;
