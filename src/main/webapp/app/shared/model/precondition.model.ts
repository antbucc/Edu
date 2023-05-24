import { IActivity } from 'app/shared/model/activity.model';

export interface IPrecondition {
  id?: string;
  title?: string | null;
  activity?: IActivity | null;
}

export const defaultValue: Readonly<IPrecondition> = {};
