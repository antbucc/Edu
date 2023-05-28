import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LearnerPreference from './learner-preference';
import LearnerPreferenceDetail from './learner-preference-detail';
import LearnerPreferenceUpdate from './learner-preference-update';
import LearnerPreferenceDeleteDialog from './learner-preference-delete-dialog';

const LearnerPreferenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LearnerPreference />} />
    <Route path="new" element={<LearnerPreferenceUpdate />} />
    <Route path=":id">
      <Route index element={<LearnerPreferenceDetail />} />
      <Route path="edit" element={<LearnerPreferenceUpdate />} />
      <Route path="delete" element={<LearnerPreferenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LearnerPreferenceRoutes;
