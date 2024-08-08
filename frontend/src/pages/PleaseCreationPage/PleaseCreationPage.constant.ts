const PLEASE_INPUT_INFOS = [
  {
    name: 'title',
    title: '제목',
    type: 'text',
    placeholder: '1자 이상 20자 이하로 입력해주세요',
    required: true,
  },
  {
    name: 'description',
    title: '상세 내용 작성',
    type: 'textarea',
    placeholder: '5자 이상으로 입력해주세요',
    required: true,
  },
] as const;

export default PLEASE_INPUT_INFOS;
