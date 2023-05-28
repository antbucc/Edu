import { IEducatorPreference } from 'app/shared/model/educator-preference.model';

export interface IPreferredTopic {
  id?: string;
  topicId?: number | null;
  topic?: string | null;
  educatorPreference?: IEducatorPreference | null;
}

export const defaultValue: Readonly<IPreferredTopic> = {};
