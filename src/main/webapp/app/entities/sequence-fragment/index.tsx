import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SequenceFragment from './sequence-fragment';
import SequenceFragmentDetail from './sequence-fragment-detail';
import SequenceFragmentUpdate from './sequence-fragment-update';
import SequenceFragmentDeleteDialog from './sequence-fragment-delete-dialog';

const SequenceFragmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SequenceFragment />} />
    <Route path="new" element={<SequenceFragmentUpdate />} />
    <Route path=":id">
      <Route index element={<SequenceFragmentDetail />} />
      <Route path="edit" element={<SequenceFragmentUpdate />} />
      <Route path="delete" element={<SequenceFragmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SequenceFragmentRoutes;
