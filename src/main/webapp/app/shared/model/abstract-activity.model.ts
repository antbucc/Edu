import { IGoal } from 'app/shared/model/goal.model';
import { IFragment } from 'app/shared/model/fragment.model';

export interface IAbstractActivity {
  id?: string;
  title?: string | null;
  activities?: IGoal[] | null;
  fragments?: IFragment[] | null;
}

export const defaultValue: Readonly<IAbstractActivity> = {};
