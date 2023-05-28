import { ILearner } from 'app/shared/model/learner.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';
import { DisabilityType } from 'app/shared/model/enumerations/disability-type.model';

export interface ILearnerPreference {
  id?: string;
  title?: string | null;
  style?: LearningStyleType | null;
  modality?: ModalityType | null;
  difficulty?: Difficulty | null;
  disability?: DisabilityType | null;
  learner?: ILearner | null;
}

export const defaultValue: Readonly<ILearnerPreference> = {};
