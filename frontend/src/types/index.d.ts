export interface MoimInfo {
  moimId: number;
  title: string;
  date: string;
  time: string;
  place: string;
  currentPeople: number;
  maxPeople: number;
  currentPeople: number;
  authorNickname: string;
  participants: string[];
  description?: string;
}

export type MoimInputInfo = Omit<
  MoimInfo,
  'moimId' | 'currentPeople' | 'participants'
>;
