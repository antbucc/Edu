import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredLearningStyle from './preferred-learning-style';
import PreferredLearningStyleDetail from './preferred-learning-style-detail';
import PreferredLearningStyleUpdate from './preferred-learning-style-update';
import PreferredLearningStyleDeleteDialog from './preferred-learning-style-delete-dialog';

const PreferredLearningStyleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredLearningStyle />} />
    <Route path="new" element={<PreferredLearningStyleUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredLearningStyleDetail />} />
      <Route path="edit" element={<PreferredLearningStyleUpdate />} />
      <Route path="delete" element={<PreferredLearningStyleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredLearningStyleRoutes;
