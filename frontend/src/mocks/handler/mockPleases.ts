export let pleasesData = [
  {
    pleaseId: 1,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
  {
    pleaseId: 2,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
  {
    pleaseId: 3,
    title: 'msw',
    description: '가나다라 마바사 아자차카타파하',
    isInterested: false,
    interestCount: 3,
  },
  {
    pleaseId: 7,
    title: '제목',
    interestCount: 1,
    description: '설명',
  },
  {
    pleaseId: 8,
    title: '제목',
    interestCount: 1,
    description: '설명',
  },
  {
    pleaseId: 9,
    title: '제목',
    interestCount: 1,
    description: '설명',
  },
  {
    pleaseId: 10,
    title: '제목',
    interestCount: 1,
    description: '설명',
  },
];

export function updatePlease(pleaseId: number, isInterested: boolean) {
  pleasesData = pleasesData.map((please) =>
    please.pleaseId === pleaseId
      ? {
          ...please,
          isInterested,
          interestCount: isInterested
            ? please.interestCount + 1
            : please.interestCount - 1,
        }
      : please,
  );
}
