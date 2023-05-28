import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Scenario from './scenario';
import Learner from './learner';
import Educator from './educator';
import Domain from './domain';
import Competence from './competence';
import Concept from './concept';
import Activity from './activity';
import Fragment from './fragment';
import Precondition from './precondition';
import Effect from './effect';
import Goal from './goal';
import AbstractActivity from './abstract-activity';
import Sequence from './sequence';
import Order from './order';
import SetOf from './set-of';
import SequenceFragment from './sequence-fragment';
import Module from './module';
import LearningDisability from './learning-disability';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="scenario/*" element={<Scenario />} />
        <Route path="learner/*" element={<Learner />} />
        <Route path="educator/*" element={<Educator />} />
        <Route path="domain/*" element={<Domain />} />
        <Route path="competence/*" element={<Competence />} />
        <Route path="concept/*" element={<Concept />} />
        <Route path="activity/*" element={<Activity />} />
        <Route path="fragment/*" element={<Fragment />} />
        <Route path="precondition/*" element={<Precondition />} />
        <Route path="effect/*" element={<Effect />} />
        <Route path="goal/*" element={<Goal />} />
        <Route path="abstract-activity/*" element={<AbstractActivity />} />
        <Route path="sequence/*" element={<Sequence />} />
        <Route path="order/*" element={<Order />} />
        <Route path="set-of/*" element={<SetOf />} />
        <Route path="sequence-fragment/*" element={<SequenceFragment />} />
        <Route path="module/*" element={<Module />} />
        <Route path="learning-disability/*" element={<LearningDisability />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
