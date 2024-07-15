type InputType = 'text' | 'textarea' | 'time' | 'date' | 'number';
export interface InputInfoType {
  title: string;
  type: InputType;
  placeholder: string;
  required: boolean;
}

export const Input_Info_List: Record<string, InputInfoType> = {
  title: {
    title: '제목',
    type: 'text',
    placeholder: '없음',
    required: true,
  },
  date: {
    title: '날짜',
    type: 'date',
    placeholder: '없음',
    required: true,
  },
  time: {
    title: '시간',
    type: 'time',
    placeholder: '없음',
    required: true,
  },
  place: {
    title: '장소',
    type: 'text',
    placeholder: '없음',
    required: true,
  },
  maxPeople: {
    title: '최대인원수',
    type: 'text',
    placeholder: '0명',
    required: true,
  },
  authorNickname: {
    title: '작성자 닉네임',
    type: 'text',
    placeholder: '알았다 안나야~',
    required: true,
  },
  description: {
    title: '상세 내용 작성',
    type: 'textarea',
    placeholder: '어떤 모임인지 작성해주세요!',
    required: false,
  },
};
