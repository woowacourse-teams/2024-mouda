export interface MoimInfo {
  moimId: number;
  title: string;
  date: string;
  time: string;
  place: string;
  maxPeople: number;
  currentPeople: number;
  authorNickname: string;
  description?: string;
}

export type MoimInputInfo = Omit<MoimInfo, 'moimId' | 'currentPeople'>;
