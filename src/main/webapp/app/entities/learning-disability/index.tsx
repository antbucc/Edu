import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LearningDisability from './learning-disability';
import LearningDisabilityDetail from './learning-disability-detail';
import LearningDisabilityUpdate from './learning-disability-update';
import LearningDisabilityDeleteDialog from './learning-disability-delete-dialog';

const LearningDisabilityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LearningDisability />} />
    <Route path="new" element={<LearningDisabilityUpdate />} />
    <Route path=":id">
      <Route index element={<LearningDisabilityDetail />} />
      <Route path="edit" element={<LearningDisabilityUpdate />} />
      <Route path="delete" element={<LearningDisabilityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LearningDisabilityRoutes;
