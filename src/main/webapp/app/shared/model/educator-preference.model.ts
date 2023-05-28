import { IPreferredActivity } from 'app/shared/model/preferred-activity.model';
import { IEducator } from 'app/shared/model/educator.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';
import { ModalityType } from 'app/shared/model/enumerations/modality-type.model';
import { Difficulty } from 'app/shared/model/enumerations/difficulty.model';

export interface IEducatorPreference {
  id?: string;
  title?: string | null;
  style?: LearningStyleType | null;
  modality?: ModalityType | null;
  difficulty?: Difficulty | null;
  preferredActivities?: IPreferredActivity[] | null;
  educator?: IEducator | null;
}

export const defaultValue: Readonly<IEducatorPreference> = {};
