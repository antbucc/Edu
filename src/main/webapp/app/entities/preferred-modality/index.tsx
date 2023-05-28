import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredModality from './preferred-modality';
import PreferredModalityDetail from './preferred-modality-detail';
import PreferredModalityUpdate from './preferred-modality-update';
import PreferredModalityDeleteDialog from './preferred-modality-delete-dialog';

const PreferredModalityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredModality />} />
    <Route path="new" element={<PreferredModalityUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredModalityDetail />} />
      <Route path="edit" element={<PreferredModalityUpdate />} />
      <Route path="delete" element={<PreferredModalityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredModalityRoutes;
