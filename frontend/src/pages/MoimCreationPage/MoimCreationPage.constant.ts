const MOIM_INPUT_INFOS = [
  {
    name: 'title',
    title: '제목',
    type: 'text',
    placeholder: '1자 이상 20자 이하로 입력해주세요',
    required: true,
  },
  {
    name: 'date',
    title: '날짜',
    type: 'date',
    placeholder: '현재 시간 이후로 설정해주세요',
    required: false,
  },
  {
    name: 'time',
    title: '시간',
    type: 'time',
    placeholder: '현재 시간 이후로 설정해주세요',
    required: false,
  },
  {
    name: 'place',
    title: '장소',
    type: 'text',
    placeholder: '1자 이상 100자 이하로 입력해주세요',
    required: false,
  },
  {
    name: 'maxPeople',
    title: '최대인원수',
    type: 'number',
    placeholder: '1명 이상 99명 이하로 입력해주세요',
    required: true,
  },
  {
    name: 'description',
    title: '상세 내용 작성',
    type: 'textarea',
    placeholder: '어떤 모임인지 작성해주세요!',
    required: false,
  },
] as const;

export default MOIM_INPUT_INFOS;
