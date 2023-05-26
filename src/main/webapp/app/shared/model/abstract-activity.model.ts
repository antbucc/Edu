import { IGoal } from 'app/shared/model/goal.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface IAbstractActivity {
  id?: string;
  title?: string;
  goals?: IGoal[] | null;
  fragment?: IFragment | null;
}

export const defaultValue: Readonly<IAbstractActivity> = {};
