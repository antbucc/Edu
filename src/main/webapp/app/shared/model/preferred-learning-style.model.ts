import { IEducatorPreference } from 'app/shared/model/educator-preference.model';
import { LearningStyleType } from 'app/shared/model/enumerations/learning-style-type.model';

export interface IPreferredLearningStyle {
  id?: string;
  style?: LearningStyleType | null;
  educatorPreference?: IEducatorPreference | null;
}

export const defaultValue: Readonly<IPreferredLearningStyle> = {};
