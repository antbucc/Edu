import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Module1 from './module-1';
import Module1Detail from './module-1-detail';
import Module1Update from './module-1-update';
import Module1DeleteDialog from './module-1-delete-dialog';

const Module1Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Module1 />} />
    <Route path="new" element={<Module1Update />} />
    <Route path=":id">
      <Route index element={<Module1Detail />} />
      <Route path="edit" element={<Module1Update />} />
      <Route path="delete" element={<Module1DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default Module1Routes;
