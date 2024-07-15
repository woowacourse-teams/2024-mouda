export interface Moim {
  title: string;
  date: string;
  time: string;
  place: string;
  maxPeople: number;
  authorNickname: string;
  description?: string;
}

export interface GetMoim {
  data: Moim[];
}

export interface PostMoim {
  id: number;
}
