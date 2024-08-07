const PLEASE_INPUT_INFOS = [
  {
    name: 'title',
    title: '제목',
    type: 'text',
    placeholder: '없음',
    required: true,
  },
  {
    name: 'description',
    title: '상세 내용 작성',
    type: 'textarea',
    placeholder: '어떤 모임이 필요한지 작성해주세요!',
    required: true,
  },
] as const;

export default PLEASE_INPUT_INFOS;
