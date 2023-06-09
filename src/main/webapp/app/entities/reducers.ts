import scenario from 'app/entities/scenario/scenario.reducer';
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
import order from 'app/entities/order/order.reducer';
import setOf from 'app/entities/set-of/set-of.reducer';
import sequenceFragment from 'app/entities/sequence-fragment/sequence-fragment.reducer';
import module from 'app/entities/module/module.reducer';
import learningDisability from 'app/entities/learning-disability/learning-disability.reducer';
import educatorPreference from 'app/entities/educator-preference/educator-preference.reducer';
import preferredTopic from 'app/entities/preferred-topic/preferred-topic.reducer';
import preferredActivity from 'app/entities/preferred-activity/preferred-activity.reducer';
import preferredModality from 'app/entities/preferred-modality/preferred-modality.reducer';
import preferredLearningStyle from 'app/entities/preferred-learning-style/preferred-learning-style.reducer';
import learnerPreference from 'app/entities/learner-preference/learner-preference.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  scenario,
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
  order,
  setOf,
  sequenceFragment,
  module,
  learningDisability,
  educatorPreference,
  preferredTopic,
  preferredActivity,
  preferredModality,
  preferredLearningStyle,
  learnerPreference,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
