export interface MoimInfo {
  title: string;
  date: string;
  time: string;
  place: string;
  maxPeople: number;
  authorNickname: string;
  description?: string;
}

export interface GetMoim {
  data: { moims: MoimInfo[] };
}

export interface PostMoim {
  id: number;
}
