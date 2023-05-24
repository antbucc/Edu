import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sequence from './sequence';
import SequenceDetail from './sequence-detail';
import SequenceUpdate from './sequence-update';
import SequenceDeleteDialog from './sequence-delete-dialog';

const SequenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sequence />} />
    <Route path="new" element={<SequenceUpdate />} />
    <Route path=":id">
      <Route index element={<SequenceDetail />} />
      <Route path="edit" element={<SequenceUpdate />} />
      <Route path="delete" element={<SequenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SequenceRoutes;
