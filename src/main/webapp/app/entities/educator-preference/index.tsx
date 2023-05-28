import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EducatorPreference from './educator-preference';
import EducatorPreferenceDetail from './educator-preference-detail';
import EducatorPreferenceUpdate from './educator-preference-update';
import EducatorPreferenceDeleteDialog from './educator-preference-delete-dialog';

const EducatorPreferenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EducatorPreference />} />
    <Route path="new" element={<EducatorPreferenceUpdate />} />
    <Route path=":id">
      <Route index element={<EducatorPreferenceDetail />} />
      <Route path="edit" element={<EducatorPreferenceUpdate />} />
      <Route path="delete" element={<EducatorPreferenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EducatorPreferenceRoutes;
