import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredTopic from './preferred-topic';
import PreferredTopicDetail from './preferred-topic-detail';
import PreferredTopicUpdate from './preferred-topic-update';
import PreferredTopicDeleteDialog from './preferred-topic-delete-dialog';

const PreferredTopicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredTopic />} />
    <Route path="new" element={<PreferredTopicUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredTopicDetail />} />
      <Route path="edit" element={<PreferredTopicUpdate />} />
      <Route path="delete" element={<PreferredTopicDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredTopicRoutes;
