import { IPreferredTopic } from 'app/shared/model/preferred-topic.model';
import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { IPreferredModality } from 'app/shared/model/preferred-modality.model';
import { IPreferredLearningStyle } from 'app/shared/model/preferred-learning-style.model';
import { IEducator } from 'app/shared/model/educator.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IEducatorPreference {
  id?: string;
  subject?: string | null;
  difficulty?: Difficulty | null;
  preferredTopics?: IPreferredTopic[] | null;
  preferredActivities?: IPreferredActivity[] | null;
  preferredModalities?: IPreferredModality[] | null;
  preferredLearningStyles?: IPreferredLearningStyle[] | null;
  eductarPreferences?: IEducator | null;
}

export const defaultValue: Readonly<IEducatorPreference> = {};
