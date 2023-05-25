import { IFragment } from 'app/shared/model/fragment.model';
import { IGoal } from 'app/shared/model/goal.model';

export interface IAbstractActivity {
  id?: string;
  title?: string | null;
  fragments?: IFragment[] | null;
  goals?: IGoal[] | null;
}

export const defaultValue: Readonly<IAbstractActivity> = {};
