import scenario from 'app/entities/scenario/scenario.reducer';
import module from 'app/entities/module/module.reducer';
import learner from 'app/entities/learner/learner.reducer';
import educator from 'app/entities/educator/educator.reducer';
import domain from 'app/entities/domain/domain.reducer';
import competence from 'app/entities/competence/competence.reducer';
import concept from 'app/entities/concept/concept.reducer';
import activity from 'app/entities/activity/activity.reducer';
import fragment from 'app/entities/fragment/fragment.reducer';
import precondition from 'app/entities/precondition/precondition.reducer';
import effect from 'app/entities/effect/effect.reducer';
import goal from 'app/entities/goal/goal.reducer';
import abstractActivity from 'app/entities/abstract-activity/abstract-activity.reducer';
import sequence from 'app/entities/sequence/sequence.reducer';
import set from 'app/entities/set/set.reducer';
import setOf from 'app/entities/set-of/set-of.reducer';
import sequenceFragment from 'app/entities/sequence-fragment/sequence-fragment.reducer';
import setOfFragment from 'app/entities/set-of-fragment/set-of-fragment.reducer';
import order from 'app/entities/order/order.reducer';
import fragmentOrder from 'app/entities/fragment-order/fragment-order.reducer';
import fragmentSet from 'app/entities/fragment-set/fragment-set.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  scenario,
  module,
  learner,
  educator,
  domain,
  competence,
  concept,
  activity,
  fragment,
  precondition,
  effect,
  goal,
  abstractActivity,
  sequence,
  set,
  setOf,
  sequenceFragment,
  setOfFragment,
  order,
  fragmentOrder,
  fragmentSet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
